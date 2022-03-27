package com.mimimiii.battleTools.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class RequestBuilder {
    static final String CONTENT_TYPE = "Content-Type: ";
    static final String CONTENT_LENGTH = "Content-Length: ";

    public static Request buildRequest(BufferedReader in) throws IOException {
        String line = in.readLine();
        Request request = new Request();

        if (line != null) {
            String[] splitFirstLine = line.split(" "); // http bezogene s leerzeichen
            Boolean hasParams = splitFirstLine[1].indexOf("?") != -1;  // abfrage ob parameter in der abfrage inkludiert.


            request.setCrud_Method(getCrud_Method(splitFirstLine));
            request.setPathname(getPathname(splitFirstLine, hasParams));
            request.setParams(getParams(splitFirstLine, hasParams));

            while (!line.isEmpty()) {
                line = in.readLine();
                if (line.startsWith(CONTENT_LENGTH)) {
                    request.setContentLength(getContentLength(line));
                }
                if (line.startsWith(CONTENT_TYPE)) {
                    request.setContentType(getContentType(line));
                }
            }

            if (request.getCrud_Method() == Crud_Method.POST || request.getCrud_Method() == Crud_Method.PUT) {
                int asciChar;
                for (int i = 0; i < request.getContentLength(); i++) {
                    asciChar = in.read();
                    String body = request.getBody();
                    request.setBody(body + ((char) asciChar));
                }
            }
        }

        return request;
    }


    private static Crud_Method getCrud_Method(String[] splitFirstLine) {
        return Crud_Method.valueOf(splitFirstLine[0].toUpperCase(Locale.ROOT));  // finds out which http_method, get,post....
    }

    private static String getPathname(String[] splitFirstLine, Boolean hasParams) {
        if (hasParams) { // splitFirstLine[1] ohne parameter... // wenn keinbe dann koomplette url path
            return splitFirstLine[1].split("\\?")[0];   // vor dem Fragezeichen der schlüssel, danach der value. format=plain
        } // backslash für escape vo de Fragezichen.

        return splitFirstLine[1];
    }


    private static String getParams(String[] separatedFirstLine, Boolean hasParams) {
        if (hasParams) {                                                                // zum beispiel ohne die deck !!!
            return separatedFirstLine[1].split("\\?")[1]; // TODO unittest für wenn kein key! curl -X GET http://localhost:10001/deck?format=plain
        }
        return "";
    }

    private static Integer getContentLength(String line) {
        return Integer.parseInt(line.substring(CONTENT_LENGTH.length())); // lies die nummer an bytes, nachricht auf vollständigkeit prüfen.
    }

    private static String getContentType(String line) {
        return line.substring(CONTENT_TYPE.length());
    }
}

