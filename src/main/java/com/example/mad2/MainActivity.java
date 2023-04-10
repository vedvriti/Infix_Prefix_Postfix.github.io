package com.example.mad2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    static int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    // The main method that converts given infix expression
    // to postfix expression.
    static String infixToPostfix(String exp)
    {
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result += c;

                // If the scanned character is an '(', push it to the stack.
            else if (c == '(')
                stack.push(c);

                // If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')')
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result += stack.pop();

                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // invalid expression
                else
                    stack.pop();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())){
                    if(stack.peek() == '(')
                        return "Invalid Expression";
                    result += stack.pop();
                }
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result += stack.pop();
        }
        return result;
    }

    static boolean isOperand(char x)
    {
        return (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z');
    }
    static String postfixToInfix(String postfix_exp){
        Stack<String> s = new Stack<String>();
        for(int i = 0; i<postfix_exp.length(); i++)
        {
            if(isOperand(postfix_exp.charAt(i))){
                s.push(postfix_exp.charAt(i) + "");
            }
            else
            {
                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();
                s.push("(" + op2 + postfix_exp.charAt(i) + op1 + ")");
            }
        }
        return s.peek();
    }
    static boolean isOperator(char x){
        switch(x){
            case '+':
            case '-':
            case '/':
            case '*':
                return true;
        }
        return false;
    }
    static String prefixToInfix(String prefix_exp) {
        Stack<String> s= new Stack<String>();

        int l = prefix_exp.length();

        for(int i = l-1; i >= 0; i--){

            if(isOperator(prefix_exp.charAt(i))){

                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();

                String temp = "(" + op1 + prefix_exp.charAt(i) + op2 + ")";

                s.push(temp);
            }

            else{
                s.push(prefix_exp.charAt(i)+"");
            }
        }

        return s.peek();
    }
    static String prefixToPostfix(String prefix_exp){

        Stack<String> s= new Stack<String>();

        int l = prefix_exp.length();

        for(int i = l-1; i >= 0; i--){
            if(isOperator(prefix_exp.charAt(i))){

                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();

                String temp = op1 + op2 + prefix_exp.charAt(i);

                s.push(temp);
            }

            else{
                s.push(prefix_exp.charAt(i)+"");
            }
        }

        return s.peek();
    }
    static String postfixToPrefix(String postfix_exp){
        Stack<String> s = new Stack<String>();

        int l = postfix_exp.length();

        for(int i = 0; i<l; i++){

            if(isOperator(postfix_exp.charAt(i))){

                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();

                String temp = postfix_exp.charAt(i) + op2 + op1;

                s.push(temp);
            }

            else{
                s.push(postfix_exp.charAt(i) + "");
            }
        }

        return s.peek();
    }
    Button postfix,prefix,infix;
    EditText result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postfix=(Button)findViewById(R.id.btn_postfix);
        postfix.setOnClickListener(this);
        prefix=(Button)findViewById(R.id.btn_prefix);
        prefix.setOnClickListener(this);
        infix=(Button)findViewById(R.id.btn_infix);
        infix.setOnClickListener(this);
        result=(EditText)findViewById(R.id.editText);
        result.setText("");
    }
    @Override
    public void onClick(View view)
    {
        if(view.equals(postfix))
        {
            String string = result.getText().toString();
            int i=string.length();
            String rest;
            char[] text=string.toCharArray();
            if(text[i-1]=='+'||text[i-1]=='-'||text[i-1]=='*'||text[i-1]=='/'||text[i-1]=='^')
                rest=string;
            else if(text[0]=='+'||text[0]=='-'||text[0]=='*'||text[0]=='/'||text[0]=='^')
                rest=prefixToPostfix(string);
            else
                rest=infixToPostfix(string);


            // It will show the output in the second edit text that we created
            result.setText(rest);
        }
        if(view.equals(prefix))
        {
            String string = result.getText().toString();
            int i=string.length();
            StringBuilder rest;
            char[] text=string.toCharArray();
            if(text[0]=='+'||text[0]=='-'||text[0]=='*'||text[0]=='/'||text[0]=='^')
                result.setText(string);
            else if(text[i-1]=='+'||text[i-1]=='-'||text[i-1]=='*'||text[i-1]=='/'||text[i-1]=='^')
            {
                String answer=postfixToPrefix(string);
                result.setText(answer);
            }
            else
            {   String postx=infixToPostfix(string);
                String ans=postfixToPrefix(postx);
                result.setText(ans);
            }

        }
        if(view.equals(infix))
        {
            String string = result.getText().toString();
            int i=string.length();
            String rest;
            char[] text=string.toCharArray();
            if(text[i-1]=='+'||text[i-1]=='-'||text[i-1]=='*'||text[i-1]=='/'||text[i-1]=='^')
                rest=(postfixToInfix(string));
            else if(text[0]=='+'||text[0]=='-'||text[0]=='*'||text[0]=='/'||text[0]=='^')
                rest=prefixToInfix(string);
            else
                rest=string;
            result.setText(rest);
        }
    }
}