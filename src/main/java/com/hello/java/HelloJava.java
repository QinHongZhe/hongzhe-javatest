

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.gientech.ivics.common.constants.Constants;
import com.gientech.ivics.common.constants.NumberConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HelloJava {


    /**
     * 日志打印.
     */
    private static final Log LOG = LogFactory.get();

    private static ExecutorService pool = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE,
            NumberConstants.THREE, TimeUnit.SECONDS,
            new SynchronousQueue<>());


    public static void main(String[] args){

        Process process = null;
        InputStream pIn = null;
        InputStream pErr = null;
        NotFileStreamGobbler outputGobbler = null;
        NotFileStreamGobbler errorGobbler = null;
        Future<Integer> executeFuture = null;
        try {

            ProcessBuilder builder = new ProcessBuilder(
                    new String[]{"/bin/sh", "-c", "sudo chmod 777" + "docker exec -it 9d5531274530 mysqladmin -h 10.158.103.224 -P13307 -uroot -proot -f drop ivics-java-unit"});

            // process = Runtime.getRuntime().exec(
            //         );
            builder.redirectErrorStream(true);

            process = builder.start();

            final Process p = process;


            pIn = process.getInputStream();
            outputGobbler = new NotFileStreamGobbler(pIn,
                    Constants.OUTPUT);
            outputGobbler.start();

            pErr = process.getErrorStream();
            errorGobbler = new NotFileStreamGobbler(pErr,
                    Constants.ERROR);
            errorGobbler.start();


            // create a Callable for the command's Process which
            // can be called by an Executor
            Callable<Integer> call = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    p.waitFor();
                    return p.exitValue();
                }
            };

            // submit the command's call and get the result from a
            executeFuture = pool.submit(call);
            LOG.debug("执行结果1: {}", JSON.toJSONString(executeFuture));
            LOG.debug("执行结果2: {}", process.waitFor());
            if (process.waitFor() == 0) {
                LOG.debug(Constants.EXEC_COMPLETE);
            }

            // close process's output stream.
            p.getOutputStream().close();

        } catch (IOException ex) {
            LOG.debug(Constants.EXEC_FAIL, ex);
        } catch (InterruptedException e) {
            LOG.debug(Constants.EXEC_FAIL, e);
            Thread.currentThread().interrupt();
        } finally {
            closeProcess(executeFuture);
            IOUtils.close(pIn);
            IOUtils.close(pErr);
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * 关闭进程.
     *
     * @param executeFuture
     */
    public static void closeProcess(final Future<Integer> executeFuture) {
        if (executeFuture != null) {
            try {
                executeFuture.cancel(true);
            } catch (Exception ignore) {
                LOG.debug(ignore.getMessage());
            }
        }
    }


}
