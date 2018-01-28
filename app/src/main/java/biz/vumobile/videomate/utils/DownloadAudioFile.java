//package biz.vumobile.videomate.utils;
//
//import android.os.AsyncTask;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//
//import static biz.vumobile.videomate.utils.MyConstraints.FILE_PATH;
//
//public class DownloadAudioFile extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... urlAndName) {
//            int count;
//            try {
//                URL url1 = new URL(urlAndName[0]);
//                URLConnection connection = url1.openConnection();
//                connection.connect();
//                // This will be useful so that you can show a typical 0-100% progress bar
//                int lengthOfFile = connection.getContentLength();
//
//                // Download the file
//                File file = new File(FILE_PATH);
//                file.mkdir();
//
//                InputStream input = new BufferedInputStream(url1.openStream());
//                OutputStream output = new FileOutputStream(FILE_PATH + urlAndName[1]);
//
//                byte data[] = new byte[1024];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // Publishing the progress....
//                    publishProgress((int) (total * 100 / lengthOfFile));
//                    output.write(data, 0, count);
//                }
//
//                output.flush();
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//
//
//    }
//}