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

//        try {
            Connection coneccion = Jsoup.connect(url);
            Connection.Response res = coneccion.execute();

            int lines = res.body().split("\n").length;
            System.out.println("(a) Cantidad de lineas de la pagina: " + lines);

            Document doc = res.parse();

            System.out.println("(b) Cantidad de parrafos de la pagina: " + doc.select("p").size());

            System.out.println("(c) Cantidad de imagenes dentro de los parrafos la pagina: " + doc.select("p > img").size());

            System.out.println("(d GET) Cantidad de formularios con método GET:" + doc.select("form[method='get']").size());

            Elements forms = doc.select("form[method='post']");
            System.out.println("(d POST) Cantidad de formularios con método POST:" + doc.select("form[method='post']").size());

            System.out.println("(e) Campos de tipo input con sus tipos:");
            Elements inputs = doc.select("input");
            for (int i = 0; i < inputs.size(); i++) {
                System.out.println("\tTipo del input #" + i + ":" + inputs.get(i).attr("type"));
            }

            System.out.println("(f)");
        for (Element form :
                forms) {
            Connection.Response formRes = Jsoup.connect(form.attr("abs:action"))
                    .method(Connection.Method.POST)
                    .data("asignatura", "practica1")
                    .header("matricula", "20132013")
                    .execute();

            System.out.println(formRes.headers());
        }

//        } catch (Exception e) {
//            System.out.println("(!!) Url invalida.");
//        }

        System.out.println("Fin del programa.");
        scan.close();
    }

}
