package webUtils;


import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class WebThread implements Runnable {
    private final Thread thread;
    private StringBuilder data;
    private final URL url;
    public boolean hasExceptions = false;


    private final Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
            hasExceptions = true;
        }
    };

    public WebThread(URI uri) throws MalformedURLException {
        thread = new Thread(this);

        thread.setDaemon(true);
        this.url = uri.toURL();
    }

    public void start() {
        thread.setUncaughtExceptionHandler(exceptionHandler);
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    public Thread.State getStatus() {
        return thread.getState();
    }

    public String getData() {
        if (data == null) {
            return "null";
        }

        return data.toString();
    }

    public void cleanData() {
        data = new StringBuilder();
    }

    @Override
    public void run() {
        StringBuilder sBuilder = new StringBuilder();

        try {
            URLConnection con = url.openConnection();
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            while ((line = br.readLine()) != null) {
                sBuilder.append(line);
            }

            data = sBuilder;

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
