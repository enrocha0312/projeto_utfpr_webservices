package br.projetowebservices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2, 10, 10));
        panel.setBounds(250,150,300,200);

        JLabel labelNome = new JLabel("Nome:");
        JLabel labelCidade = new JLabel("Cidade:");
        JLabel labelOperacao = new JLabel("Operação:");
        JLabel labelID = new JLabel("ID para GET,DELETE e PUT");
        JLabel titulo = new JLabel("Webservices Project");
        titulo.setBounds(300,80,300,50);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBackground(Color.MAGENTA);
        JTextField textFieldNome = new JTextField();
        JTextField textFieldID = new JTextField();
        String[] cidades = {"Nova Friburgo", "Teresópolis", "Petrópolis", "Itatiaia"};
        JComboBox<String> comboBoxCidades = new JComboBox<>(cidades);
        String[] options = {"Post", "Get", "Delete", "Update", "FindAll"};
        JComboBox<String> comboBoxOpcoes = new JComboBox<>(options);
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(Color.green);
        btnEnviar.setForeground(Color.gray);
        panel.add(labelNome);
        panel.add(textFieldNome);
        panel.add(labelCidade);
        panel.add(comboBoxCidades);
        panel.add(labelOperacao);
        panel.add(comboBoxOpcoes);
        panel.add(labelID);
        panel.add(textFieldID);
        panel.add(btnEnviar);
        frame.add(titulo);
        frame.add(panel);
        frame.setVisible(true);

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcaoSelecionada = comboBoxOpcoes.getSelectedItem().toString();
                String cidade = comboBoxCidades.getSelectedItem().toString();
                String cliente = textFieldNome.getText();
                if(opcaoSelecionada.equals("Post")){
                    try {
                        String resposta = cadastrarCliente(cliente, cidade);
                        JDialog dialog = new JDialog();
                        JLabel labelRespostaJson = new JLabel("Resposta Json: " + resposta);
                        dialog.add(labelRespostaJson);
                        dialog.setBounds(100, 200, 300, 300);
                        dialog.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(opcaoSelecionada.equals("FindAll")){
                    try {
                        String resposta = retornarClientes();
                        JDialog dialog = new JDialog();
                        JLabel labelRespostaJson = new JLabel("Resposta Json: " + resposta);
                        dialog.add(labelRespostaJson);
                        dialog.setBounds(100, 200, 300, 300);
                        dialog.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(opcaoSelecionada.equals("Get")){
                    try {
                        String resposta = retornarClientePorId(textFieldID.getText());
                        JDialog dialog = new JDialog();
                        JLabel labelRespostaJson = new JLabel("Resposta Json: " + resposta);
                        dialog.add(labelRespostaJson);
                        dialog.setBounds(100, 200, 300, 300);
                        dialog.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(opcaoSelecionada.equals("Delete")){
                    try {
                        String resposta = deleteClientePorId(textFieldID.getText());
                        JDialog dialog = new JDialog();
                        JLabel labelRespostaJson = new JLabel("Resposta: " + resposta);
                        dialog.add(labelRespostaJson);
                        dialog.setBounds(100, 200, 300, 300);
                        dialog.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(opcaoSelecionada.equals("Update")){
                    try {
                        String resposta = atualizarCliente(cliente, cidade,textFieldID.getText());
                        JDialog dialog = new JDialog();
                        JLabel labelRespostaJson = new JLabel("Resposta Json: " + resposta);
                        dialog.add(labelRespostaJson);
                        dialog.setBounds(100, 200, 300, 300);
                        dialog.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
    }

    private static String deleteClientePorId(String id) throws IOException {
        URL url = new URL("http://localhost:8092/utfpr/clientes/" + id);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("DELETE");
        StringBuilder resposta = new StringBuilder();
        if(conexao.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT){
            resposta.append("Cliente com ID " + id + " apagado");
            return resposta.toString();
        }else{
            resposta.append("Erro na requisição");
            return resposta.toString();
        }
    }
    private static String atualizarCliente(String cliente, String cidade, String id) throws IOException {
        String corpoRequisição = "{ \"nome\": \"" + cliente +"\", \"cidade\": {" +
                "\"nome\": \"" + cidade + "\" } }" ;
        URL url = new URL("http://localhost:8092/utfpr/clientes/" + id);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("PUT");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);
        StringBuilder resposta = new StringBuilder();
        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = corpoRequisição.getBytes("utf-8");
            os.write(input, 0, input.length);
        }catch (Exception e){
            resposta.append(e.getMessage());
            return resposta.toString();
        }
        if(conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                resposta.append(line);
            }
            reader.close();
            return resposta.toString();
        }else{
            resposta.append("Erro na requisição");
            return resposta.toString();
        }
    }

    private static String retornarClientePorId(String id) throws IOException {
        URL url = new URL("http://localhost:8092/utfpr/clientes/" + id);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        StringBuilder resposta = new StringBuilder();
        if(conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                resposta.append(line);
            }
            reader.close();
            return resposta.toString();
        }else{
            resposta.append("Erro na requisição");
            return resposta.toString();
        }
    }

    private static String retornarClientes() throws IOException {
        URL url = new URL("http://localhost:8092/utfpr/clientes");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        StringBuilder resposta = new StringBuilder();
        if(conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                resposta.append(line);
            }
            reader.close();
            return resposta.toString();
        }else{
            resposta.append("Erro na requisição");
            return resposta.toString();
        }
    }

    private static String cadastrarCliente(String cliente, String cidade) throws IOException, InterruptedException {
        String corpoRequisição = "{ \"nome\": \"" + cliente +"\", \"cidade\": {" +
                "\"nome\": \"" + cidade + "\" } }" ;
        URL url = new URL("http://localhost:8092/utfpr/clientes");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);
        StringBuilder resposta = new StringBuilder();
        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = corpoRequisição.getBytes("utf-8");
            os.write(input, 0, input.length);
        }catch (Exception e){
            resposta.append(e.getMessage());
            return resposta.toString();
        }
        if(conexao.getResponseCode() == HttpURLConnection.HTTP_CREATED){
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                resposta.append(line);
            }
            reader.close();
            return resposta.toString();
        }else{
            resposta.append("Erro na requisição");
            return resposta.toString();
        }
    }
}