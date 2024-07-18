package com.Natwest.task.utils;

import java.util.Stack;

public class ArithmeticUtils {
    public static float evaluate(String expression) throws Exception {
        return evaluateExpression(expression.replaceAll(" ", ""));
    }

    private static float evaluateExpression(String expr) throws Exception {
        Stack<Float> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                values.push(Float.parseFloat(sb.toString()));
                i--;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Remove '('
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && precedence(c) <= precedence(ops.peek())) {
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            } else if (c == 'm' && expr.startsWith("min", i)) {
                ops.push('m');
                i += 2;
            } else if (c == 'm' && expr.startsWith("max", i)) {
                ops.push('M');
                i += 2;
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static float applyOperation(char op, float b, float a) throws Exception {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new Exception("Division by zero");
                return a / b;
            case 'm': return Math.min(a, b);
            case 'M': return Math.max(a, b);
            default: throw new Exception("Invalid operator");
        }
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case 'm':
            case 'M':
                return 0; // Min and Max have lowest precedence
            default:
                return -1;
        }
    }
}
