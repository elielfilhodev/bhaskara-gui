package com.example.bhaskaragui.model;

/**
 * Calculadora de equações do segundo grau usando a fórmula de Bhaskara.
 * Realiza o cálculo completo e fornece a solução detalhada.
 */
public class BhaskaraCalculator {

    private static final String ERROR_A_ZERO = "ERRO: a = 0 => não é equação do 2º grau.";
    private static final String ERROR_A_ZERO_INFO = "Isso vira equação linear: bx + c = 0.";
    private static final String EQUATION_LABEL = "Equação: ax² + bx + c = 0";
    private static final String NO_REAL_ROOTS = "Δ < 0 => não existem raízes reais.";
    private static final String COMPLEX_ROOTS = "As raízes seriam complexas.";
    private static final String ONE_ROOT = "Δ = 0 => uma raiz real (raiz dupla)";
    private static final String TWO_ROOTS = "Δ > 0 => duas raízes reais distintas";

    private final double a;
    private final double b;
    private final double c;
    private final NumberFormatter formatter;

    public BhaskaraCalculator(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.formatter = new NumberFormatter();
    }

    /**
     * Resolve a equação do segundo grau completamente.
     *
     * @return String formatada com a resolução completa
     */
    public String solve() {
        StringBuilder sb = new StringBuilder();

        // Cabeçalho
        sb.append(EQUATION_LABEL).append("\n");
        sb.append("a = ").append(formatter.format(a))
          .append(", b = ").append(formatter.format(b))
          .append(", c = ").append(formatter.format(c))
          .append("\n\n");

        // Validação: a não pode ser zero
        if (formatter.isNearlyZero(a)) {
            sb.append(ERROR_A_ZERO).append("\n");
            sb.append(ERROR_A_ZERO_INFO).append("\n");
            return sb.toString();
        }

        // Calcular Delta
        double delta = calculateDelta();
        appendDeltaCalculation(sb, delta);

        // Análise do Delta
        if (delta < 0) {
            sb.append("2) Análise do Δ\n");
            sb.append(NO_REAL_ROOTS).append("\n");
            sb.append(COMPLEX_ROOTS).append("\n");
            return sb.toString();
        }

        // Aplicar Bhaskara
        appendBhaskaraFormula(sb, delta);

        return sb.toString();
    }

    /**
     * Calcula o discriminante (Delta).
     *
     * @return o valor de Δ = b² - 4ac
     */
    private double calculateDelta() {
        return b * b - 4 * a * c;
    }

    /**
     * Adiciona o cálculo do Delta ao StringBuilder.
     */
    private void appendDeltaCalculation(StringBuilder sb, double delta) {
        sb.append("1) Calcular Delta (Δ)\n");
        sb.append("Δ = b² - 4ac\n");
        sb.append("Δ = (").append(formatter.format(b)).append(")² - 4 × (")
          .append(formatter.format(a)).append(") × (").append(formatter.format(c)).append(")\n");
        sb.append("Δ = ").append(formatter.format(b * b)).append(" - ")
          .append(formatter.format(4 * a * c)).append("\n");
        sb.append("Δ = ").append(formatter.format(delta)).append("\n\n");
    }

    /**
     * Adiciona a aplicação da fórmula de Bhaskara ao StringBuilder.
     */
    private void appendBhaskaraFormula(StringBuilder sb, double delta) {
        double sqrtDelta = Math.sqrt(delta);
        double denom = 2 * a;

        sb.append("2) Aplicar Bhaskara\n");
        sb.append("x = (-b ± √Δ) / (2a)\n");
        sb.append("2a = 2 × ").append(formatter.format(a)).append(" = ")
          .append(formatter.format(denom)).append("\n");
        sb.append("√Δ = √").append(formatter.format(delta)).append(" = ")
          .append(formatter.format(sqrtDelta)).append("\n\n");

        // Calcular raízes
        double x1 = (-b + sqrtDelta) / denom;
        double x2 = (-b - sqrtDelta) / denom;

        appendRootsCalculation(sb, x1, x2, sqrtDelta, denom, delta);
    }

    /**
     * Adiciona o cálculo das raízes ao StringBuilder.
     */
    private void appendRootsCalculation(StringBuilder sb, double x1, double x2,
                                        double sqrtDelta, double denom, double delta) {
        sb.append("3) Calcular as raízes\n");
        sb.append("x₁ = (-b + √Δ) / (2a)\n");
        sb.append("x₁ = (").append(formatter.format(-b)).append(" + ")
          .append(formatter.format(sqrtDelta)).append(") / ").append(formatter.format(denom)).append("\n");
        sb.append("x₁ = ").append(formatter.format(x1)).append("\n\n");

        sb.append("x₂ = (-b - √Δ) / (2a)\n");
        sb.append("x₂ = (").append(formatter.format(-b)).append(" - ")
          .append(formatter.format(sqrtDelta)).append(") / ").append(formatter.format(denom)).append("\n");
        sb.append("x₂ = ").append(formatter.format(x2)).append("\n\n");

        appendConclusion(sb, x1, x2, delta);
    }

    /**
     * Adiciona a conclusão ao StringBuilder.
     */
    private void appendConclusion(StringBuilder sb, double x1, double x2, double delta) {
        sb.append("4) Conclusão\n");
        if (formatter.isNearlyZero(delta)) {
            sb.append(ONE_ROOT).append("\n");
            sb.append("x = ").append(formatter.format(x1)).append("\n");
        } else {
            sb.append(TWO_ROOTS).append("\n");
            sb.append("x₁ = ").append(formatter.format(x1)).append("\n");
            sb.append("x₂ = ").append(formatter.format(x2)).append("\n");
        }
    }
}