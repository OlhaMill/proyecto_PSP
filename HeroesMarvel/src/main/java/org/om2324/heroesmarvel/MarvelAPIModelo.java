package org.om2324.heroesmarvel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarvelAPIModelo {
    public static List<HeroeMarvel> GetRequest() {
        HttpURLConnection conn = null;
        List <HeroeMarvel> listaHeroes = new ArrayList<>();
        try {
            URL url = new URL(
                    "http://localhost:8080/heroes");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject)
                            jsonArray.get(i);
                    HeroeMarvel heroe = new HeroeMarvel(jsonObject.get("nombre").toString(),
                            jsonObject.get("poder").toString(), jsonObject.get("alias").toString());
                    listaHeroes.add(heroe);
                }
            } else {
                throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return listaHeroes;
    }

    public static HeroeMarvel GetHeroById(String id) {
        HttpURLConnection conn = null;
        HeroeMarvel heroe = null;
        try {
            URL url = new URL("http://localhost:8080/heroes?id=" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                JSONObject jsonObject = new JSONObject(response);
                heroe = new HeroeMarvel(jsonObject.get("nombre").toString(),
                        jsonObject.get("poder").toString(), jsonObject.get("alias").toString());
            } else if (conn.getResponseCode() == 404) {
                // Héroe no encontrado
                throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
            } else {
                throw new RuntimeException("Connection failed.\n" + conn.getResponseCode() + " " + conn.getResponseMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return heroe;
    }
    public static List<HeroeMarvel> GetHeroByNombreAlias(String nombre, String alias) {
        HttpURLConnection conn = null;
        List<HeroeMarvel> listaHeroes = new ArrayList<>();
        try {
            String nombreCodificado = URLEncoder.encode(nombre, StandardCharsets.UTF_8).replace("+", "%20");
            String aliasCodificado = URLEncoder.encode(alias, StandardCharsets.UTF_8).replace("+", "%20");

            URL url = new URL("http://localhost:8080/heroes/" + nombreCodificado + "/" + aliasCodificado);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HeroeMarvel heroe = new HeroeMarvel(jsonObject.get("nombre").toString(),
                            jsonObject.get("poder").toString(), jsonObject.get("alias").toString());
                    listaHeroes.add(heroe);
                }
            } else {
                throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return listaHeroes;
    }
    public static boolean PostRequest(HeroeMarvel heroe) {
        HttpURLConnection conn = null;
        String url = "http://localhost:8080/heroes";
        String requestBody = "{\"nombre\":\"" + heroe.getNombre() + "\", \"poder\":\"" + heroe.getPoder()+ "\", \"alias\":\"" + heroe.getAlias() + "\"}";
        System.out.println(requestBody);

        try {
            URL urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200)
                return true;
            else
                throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
        catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }
    }
    public static boolean PutRequest(String id, HeroeMarvel heroe) {
        HttpURLConnection conn = null;
        String requestBody = "{\"nombre\":\"" + heroe.getNombre() + "\", \"poder\":\"" + heroe.getPoder() + "\", \"alias\":\"" + heroe.getAlias() + "\"}";
        try {
            URL url = new URL("http://localhost:8080/heroes/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            System.out.println(requestBody);

            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200)
                return true;
            else
                throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private static boolean postPutBody(HttpURLConnection conn, String jsonInputString) throws IOException {
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200)
            return true;
        else
            throw new RuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage());
    }

    public static boolean DeleteRequest(String id) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/" +
                    "heroes/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() == 200)
                return true;
            else
                throw new RuntimeException("Connection failed");
        }
        catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }
    }
}

