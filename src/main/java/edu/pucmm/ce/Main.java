package edu.pucmm.ce;


import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.print("Ingresé una URL válida: ");
        String url = scan.nextLine();

        System.out.println("Conectandose a " + url + "...");

        try {
            Connection coneccion = Jsoup.connect(url);
            Connection.Response res = coneccion.execute();

            int lines = res.body().split("\n").length;
            System.out.println("(a) Cantidad de lineas de la pagina: " + lines);

            Document doc = res.parse();

            System.out.println("(b) Cantidad de parrafos de la pagina: " + doc.select("p").size());

            System.out.println("(c) Cantidad de imagenes dentro de los parrafos la pagina: " + doc.select("p > img").size());

            Elements forms = doc.select("form");
            Elements postForms = forms.select("form[method='post']");
            Elements getForms = forms.select("form[method='get']");

            System.out.println("(d GET) Cantidad de formularios con método GET:" + getForms.size());
            System.out.println("(d POST) Cantidad de formularios con método POST:" + postForms.size());

            System.out.println("(e) Campos de tipo input con sus tipos:");
            for (int i = 0; i < forms.size(); i++) {
                System.out.println("Form#" + (i + 1));

                Elements inputs = forms.select("input");

                for (int j = 0; j < inputs.size(); j++) {
                    System.out.println("\tEl Tipo del input #" + (j + 1) + ":" + inputs.get(j).attr("type"));
                }
            }

            System.out.println("(f) Respuesta:");
            for (Element form :
                postForms) {
                Document formDoc = Jsoup.connect(form.attr("abs:action"))
                        .data("asignatura", "practica1")
                        .header("matricula", "20132013")
                        .post();

                System.out.println(">Inicio---------------------------------------------------->");
                System.out.println(formDoc.body());
                System.out.println("<Fin-------------------------------------------------------<\n\n");
            }

        } catch (IOException ioe) {
            System.out.println("(!!) Url invalida.");
        }

        System.out.println("Fin del programa.");
        scan.close();
    }

}
