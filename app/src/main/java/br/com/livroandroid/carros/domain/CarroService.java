package br.com.livroandroid.carros.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.fragments.CarrosFragment;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.XMLUtils;

/**
 * Created by Gustavo on 28/02/2016.
 */
public class CarroService {
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";
    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";

    public static List<Carro> getCarros(Context context, String tipo, boolean refresh) throws IOException {
        //List<Carro> carros = getCarrosFromArquivo(context, tipo);
        List<Carro> carros = null;
        boolean buscaNoBancoDeDados = !refresh;
        if (buscaNoBancoDeDados) {
            carros = getCarrosFromBanco(context, tipo);
            if (carros != null && carros.size() > 0) {
                return carros;
            }
        }
        carros = getCarrosFromWebService(context, tipo);
        return carros;
    }

    public static List<Carro> getCarrosFromBanco(Context context, String tipo) throws IOException {
        CarroDB carroDB = new CarroDB(context);
        try {
            List<Carro> carros = carroDB.findAllByTipo(tipo);
            Log.d(TAG, "Retornando " + carros.size() + " carros [" + tipo + "] do banco");
            return carros;
        } finally {
            carroDB.close();
        }
    }

    public static List<Carro> getCarrosFromArquivo(Context context, String tipo) throws IOException {
        String fileName = String.format("carros_%s.json", tipo);
        Log.d(TAG, "Abrindo arquivo: " + fileName);
        String json = FileUtils.readFile(context, fileName, "UTF-8");
        if (json == null) {
            Log.d(TAG, "Arquivo " + fileName + " não encontrado.");
            return null;
        } else {
            List<Carro> carros = parserJSON(context, json);
            Log.d(TAG, "Carros lidos do arquivo " + fileName + ".");
            return carros;
        }
    }

    public static List<Carro> getCarrosFromWebService(Context context, String tipo) throws IOException {
        String url = URL.replace("{tipo}", tipo);
        String json = new HttpHelper().doGet(url);
        //salvarArquivoNaMemoriaInterna(context, url, json);
        List<Carro> carros = parserJSON(context, json);
        salvarCarros(context, tipo, carros);
        return carros;
    }

    // Faz a leitura do arquivo que esta na pasta /res/raw
    private static String readFileXML(Context context, String tipo) throws IOException {
        if ("classicos".equals(tipo)) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos_xml, "UTF-8");
        } else if ("esportivos".equals(tipo)) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos_xml, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo_xml, "UTF-8");
    }

    private static String readFileJSON(Context context, String tipo) throws IOException {
        if ("classicos".equals(tipo)) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos_json, "UTF-8");
        } else if ("esportivos".equals(tipo)) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos_json, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo_json, "UTF-8");
    }

    // Faz o parser do xml e cria a lista de carros
    private static List<Carro> parserXML(Context context, String xml) {
        List<Carro> carros = new ArrayList<Carro>();
        Element root = XMLUtils.getRoot(xml, "UTF-8");
        // Lê todas as tags <carro>
        List<Node> nodeCarros = XMLUtils.getChildren(root, "carro");
        // Insere cada carro na lista
        for (Node node : nodeCarros) {
            Carro c = new Carro();
            // Lê as informações de cada carro
            c.nome = XMLUtils.getText(node, "nome");
            c.desc = XMLUtils.getText(node, "desc");
            c.urlFoto = XMLUtils.getText(node, "url_foto");
            c.urlInfo = XMLUtils.getText(node, "url_info");
            if (LOG_ON) {
                Log.d(TAG, "Carro " + c.nome + " > " + c.urlFoto);
            }
            carros.add(c);
        }
        if (LOG_ON) {
            Log.d(TAG, carros.size() + " encontrados.");
        }
        return carros;
    }

    // Faz o parser do json e cria a lista de carros
    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<Carro>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");
            // Insere cada carro na lista
            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                // Lê as informações de cada carro
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.nome + " > " + c.urlFoto);
                }
                carros.add(c);
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return carros;
    }

    private static void salvarArquivoNaMemoriaInterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/")+1);
        File file = FileUtils.getFile(context, fileName);
        IOUtils.writeString(file, json);
        Log.d(TAG, "Arquivo salvo: " + file);
    }

    private static void salvarCarros(Context context, String tipo, List<Carro> carros) {
        CarroDB carroDB = new CarroDB(context);
        try {
            carroDB.deleteCarrosByTipo(tipo);
            for (Carro c : carros) {
                c.tipo = tipo;
                Log.d(TAG, "Salvando o carro " + c.nome);
                carroDB.save(c);
            }
        } finally {
            carroDB.close();
        }
    }
}
