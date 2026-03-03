package com.example.bhaskaragui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Locale;

public class HelloApplication extends Application {

    private static final DecimalFormat DF = new DecimalFormat("0.##########"); // até 10 casas, sem lixo

    private static double parseDoubleOrThrow(String raw, String fieldName) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Campo '" + fieldName + "' vazio.");
        }
        try {
            // aceita "2,5" e "2.5"
            String normalized = raw.trim().replace(',', '.');
            return Double.parseDouble(normalized);
        } catch (Exception e) {
            throw new IllegalArgumentException("Campo '" + fieldName + "' invalido: " + raw);
        }
    }

    private static boolean nearlyZero(double x) {
        return Math.abs(x) < 1e-12;
    }

    private static String f(double x) {
        // evita -0
        if (nearlyZero(x)) return "0";
        return DF.format(x);
    }

    private static String resolverCompleto(double a, double b, double c) {
        StringBuilder sb = new StringBuilder();

        sb.append("Equacao: ax^2 + bx + c = 0\n");
        sb.append("a = ").append(f(a)).append(", b = ").append(f(b)).append(", c = ").append(f(c)).append("\n\n");

        if (nearlyZero(a)) {
            sb.append("ERRO: a = 0 => nao e equacao do 2o grau.\n");
            sb.append("Isso vira equacao linear: bx + c = 0.\n");
            return sb.toString();
        }

        // Delta
        double delta = b * b - 4 * a * c;

        sb.append("1) Calcular Delta (Δ)\n");
        sb.append("Δ = b^2 - 4ac\n");
        sb.append("Δ = (").append(f(b)).append(")^2 - 4 * (").append(f(a)).append(") * (").append(f(c)).append(")\n");
        sb.append("Δ = ").append(f(b * b)).append(" - ").append(f(4 * a * c)).append("\n");
        sb.append("Δ = ").append(f(delta)).append("\n\n");

        if (delta < 0) {
            sb.append("2) Analise do Δ\n");
            sb.append("Δ < 0 => nao existem raizes reais.\n");
            sb.append("As raizes seriam complexas.\n");
            return sb.toString();
        }

        double sqrtDelta = Math.sqrt(delta);
        double denom = 2 * a;

        sb.append("2) Aplicar Bhaskara\n");
        sb.append("x = (-b ± √Δ) / (2a)\n");
        sb.append("2a = 2 * ").append(f(a)).append(" = ").append(f(denom)).append("\n");
        sb.append("√Δ = √").append(f(delta)).append(" = ").append(f(sqrtDelta)).append("\n\n");

        double x1 = (-b + sqrtDelta) / denom;
        double x2 = (-b - sqrtDelta) / denom;

        sb.append("3) Calcular as raizes\n");
        sb.append("x1 = (-b + √Δ) / (2a)\n");
        sb.append("x1 = (").append(f(-b)).append(" + ").append(f(sqrtDelta)).append(") / ").append(f(denom)).append("\n");
        sb.append("x1 = ").append(f(x1)).append("\n\n");

        sb.append("x2 = (-b - √Δ) / (2a)\n");
        sb.append("x2 = (").append(f(-b)).append(" - ").append(f(sqrtDelta)).append(") / ").append(f(denom)).append("\n");
        sb.append("x2 = ").append(f(x2)).append("\n\n");

        sb.append("4) Conclusao\n");
        if (nearlyZero(delta)) {
            sb.append("Δ = 0 => uma raiz real (raiz dupla)\n");
            sb.append("x = ").append(f(x1)).append("\n");
        } else {
            sb.append("Δ > 0 => duas raizes reais distintas\n");
            sb.append("x1 = ").append(f(x1)).append("\n");
            sb.append("x2 = ").append(f(x2)).append("\n");
        }

        return sb.toString();
    }

    @Override
    public void start(Stage stage) {
        Locale.setDefault(Locale.US);

        TextField aField = new TextField();
        TextField bField = new TextField();
        TextField cField = new TextField();

        aField.setPromptText("Ex: 2");
        bField.setPromptText("Ex: 5");
        cField.setPromptText("Ex: -3");

        Button calcularBtn = new Button("Calcular");
        Button limparBtn = new Button("Limpar");
        Button exemploBtn = new Button("Exemplo");

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefRowCount(16);

        calcularBtn.setOnAction(e -> {
            try {
                double a = parseDoubleOrThrow(aField.getText(), "a");
                double b = parseDoubleOrThrow(bField.getText(), "b");
                double c = parseDoubleOrThrow(cField.getText(), "c");

                output.setText(resolverCompleto(a, b, c));
            } catch (IllegalArgumentException ex) {
                output.setText("Erro: " + ex.getMessage());
            }
        });

        limparBtn.setOnAction(e -> {
            aField.clear();
            bField.clear();
            cField.clear();
            output.clear();
            aField.requestFocus();
        });

        exemploBtn.setOnAction(e -> {
            aField.setText("2");
            bField.setText("5");
            cField.setText("-3");
            output.setText(resolverCompleto(2, 5, -3));
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(18));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("a:"), 0, 0);
        grid.add(aField, 1, 0);

        grid.add(new Label("b:"), 0, 1);
        grid.add(bField, 1, 1);

        grid.add(new Label("c:"), 0, 2);
        grid.add(cField, 1, 2);

        grid.add(calcularBtn, 0, 3);
        grid.add(limparBtn, 1, 3);
        grid.add(exemploBtn, 0, 4);

        grid.add(new Label("Resolucao completa:"), 0, 5);
        grid.add(output, 0, 6, 2, 1);

        Scene scene = new Scene(grid, 520, 520);
        stage.setTitle("Bhaskara - JavaFX (Resolucao Completa)");
        stage.setScene(scene);
        stage.show();

        aField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}