import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;  
import java.util.Stack;
import javafx.scene.text.Text;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.String;
public class MyCalculator
{
    //������Ҫ�ĸ������
    String input = "";//��ʾ���ı����е��ַ���
    Frame frame = new Frame("������");//����Ĵ���
    Button[] button = new Button[23];//��Ҫ�İ�ť
    TextField textField = new TextField(15);//�ı���
    String[] name = {"sqrt","1/x","%","C","Del","7","8","9","+","-","4","5","6","*","/","1","2","3","(",")","0",".","="};//��ť������
    Panel p1 = new Panel();//װ���ı��������
    Panel p2 = new Panel();//װ�ذ�ť������
    
    public  int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = 1;
                break;
            case "-":
                result = 1;
                break;
            case "*":
                result = 2;
                break;
            case "/":
                result = 2;
                break;
            case "%":
                result = 2;
                break;
            default:
                
                break;
        }
        return result;
    }
    
    public boolean isOperator(String op){
        return op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals("(")||op.equals(")")||op.equals("sqrt")||op.equals("1/x");
    }
    public Double caculation(Double num1, Double num2, String op){
        switch(op){
            case "+":
                return num2 + num1;
            case "-":
                return num2 - num1;
            case "*":
                return num2 * num1;
            case "/":    
                return num2 / num1;
            case "%":
                return num2 % num1;
            default:
                return 0.0;
            }
    }
    public  String sqrt(String input){
        String str[] = input.split(" ");
        String r ="";
        Double t = Math.sqrt(Double.parseDouble(str[str.length-1]));
        if(str.length > 1){
            r += str[0];
        }
        if(str.length == 1)
        {
            return t.toString();
        } 
 
        for(int i =1 ;i<str.length-1;i++){
            r += " "+str[i];
        }
        r +=" "+ t.toString();
        return r;
    }
    public String reversal(String input){
        String str[] = input.split(" ");
        String r ="";
        Double t = 1 / Double.parseDouble(str[str.length-1]);
        if(str.length > 1){
            r += str[0];
        }
        if(str.length == 1)
        {
            return t.toString();
        } 
 
        for(int i =1 ;i<str.length-1;i++){
            r += " "+str[i];
        }
        r +=" "+ t.toString();
        return r;
    }
    public static String parseSuffixExpression(String ls){
        MyCalculator m = new MyCalculator();
        String str[] = ls.split(" ");
        Stack<String> s1 = new Stack<>();
        String s2 = ""; // �����м�����Lists2

        //���������ls
        
        for (String item : str) {
            System.out.println(item);
            
            if (!m.isOperator(item)){
                s2 += " "+item;
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //����������š�)���������ε���s1ջ�������������ѹ��s2��ֱ������������Ϊֹ����ʱ����һ�����Ŷ���
                while(!s1.peek().equals("(")) {
                    s2 +=" "+s1.pop();
                }
                s1.pop();//!!! �� ( ���� s1ջ�� ����С����
            }else if(item.equals("sqrt")||item.equals("1/x")){
                s2 += " "+item;
            }
            else {
                //��item�����ȼ�С�ڻ��ߵ���   s1 ջ���������
                // ��s1ջ������������������뵽s2�У��ٴ�ת��(4.1)��s1���µ�ջ���������Ƚ�
                //��дһ��Operate�࣬�ٱ�дgetValue��������ȡ��ǰ����������ȼ�
                while (s1.size() != 0 && m.getValue(s1.peek()) >=m.getValue(item)){
                    //��s1�е����������s2��ֱ����item�����ȼ�����s1ջ���������,�˳�ѭ�������ҽ�itemѹ��s1
                    s2 += " "+s1.pop();
                }
                //����Ҫ��itemѹ��ջ
                s1.push(item);
            }

        }
        //��s1��ʣ�����������ε���������s2
        while(s1.size() != 0) {
            s2 += " "+s1.pop();
        }

        return s2; //ע����Ϊ�Ǵ�ŵ�List, ��˰�˳��������Ƕ�Ӧ�ĺ�׺���ʽ��Ӧ��List


    }
    //��ջ��ʵ�ֺ�׺���ʽ�ļ���
    public  Double compute(String input){
        MyCalculator m = new MyCalculator();
        String str[] = input.split(" ");
        Stack<Double> s = new Stack<Double>();

        for(int i=0;i<str.length;i++)
        {
            if(!m.isOperator(str[i]) ){
                s.push(Double.parseDouble(str[i]));
            }
            else{
                Double result = m.caculation(s.pop(), s.pop(), str[i]);
                s.push(result);
            }

        }
        return s.pop();
    }
    public void init()
    {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        //ΪPanel���������ͼ�������úò��ּ��̲���
        textField.setFont(new Font("����",Font.PLAIN,50));//���������ʽ
        textField.setEditable(false);
        //���ı�����ӵ�p1������
        p1.add(textField);

        //���ð�ť�����Ĳ���
        p2.setLayout(new GridLayout(5,5,5,5));  

        

        //Ϊÿ��button����
        for(int i=0; i<23; i++)
        {
            button[i] = new Button(name[i]);
        }

        //���ð�ť�����ļ�����
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String buttonname = e.getActionCommand();
                input += buttonname;

                if(buttonname.equals("+")||buttonname.equals("-")||buttonname.equals("*")||buttonname.equals("/")||buttonname.equals("(")||buttonname.equals(")")||buttonname.equals("%"))
                {
                    
                    input = input.substring(0, input.length() - 1);  //����Щ����ȥ��
                    
                    if(buttonname == "("){
                        input += buttonname+" ";
                    }else if(buttonname == ")"){
                        input +=" "+buttonname;
                    }else if(buttonname == "1/x"){
                        input = input.substring(0, input.length() - 2);
                        input +=" "+buttonname;
                    }
                    else{
                        input += " "+buttonname +" ";  //�����ֺ�ӿո��ټӷ����ټӿո�Ϊ�˽�������str�е���ռһ��
                    }
                    
                }
                if(buttonname.equals("sqrt")){
                    input = input.substring(0,input.length()-4);
                    input = sqrt(input);
                    textField.setText("");
                    textField.setText(input);
                }
                if(buttonname.equals("1/x")){
                    input = input.substring(0,input.length()-3);
                    input = reversal(input);
                    textField.setText("");
                    textField.setText(input);
                }
                //������ںŵĺ����Ϊ
                if(buttonname.equals("="))
                {
                    input = input.substring(0, input.length() - 1);
                    input = parseSuffixExpression(input);
                    String str="";
                    for(int i =1;i<input.length();i++){
                        str += input.charAt(i);
                    }
                    input = compute(str).toString();
                    textField.setText(input);  //���������

                }
                //�������������Ϊ
                if(buttonname.equals("C"))
                {
                    input = "";
                }

                //����˸�������Ϊ
                if(buttonname.equals("Del"))
                {
                    input = input.substring(0, input.length() - 3);  //�Ƚ������"Del"ɾȥ
                    if(input.endsWith(" "))        //���input�ĺ��������" ",��input����������һ��������ţ�ɾȥ���ź������������ַ��ŵ�" "
                        input = input.substring(0,input.length() - 3);
                    else if(input.endsWith("t")){
                        input = input.substring(0,input.length() - 5);
                    }
                    else if(input.endsWith("x")){
                        input = input.substring(0,input.length() - 4);
                    }
                    else                                          //ɾȥһ������          
                        input = input.substring(0, input.length() - 1);
                    
                }

                //�õ����������ٵ������������ִ�����
                textField.setText(input);
            }
        };
        //��������װ��ÿ����ť��
        for(int x = 0;x<23;x++)
        {
            button[x].addActionListener(listener);
        }
        //����ťװ��p2�����У�ע��װ����������ٽ���ťװ��������
        for(int x = 0;x<23;x++) 
        {
            p2.add(button[x]);
        }
        //��װ������������
        frame.add(p1,BorderLayout.NORTH);
        frame.add(p2);

        //����frame���ڴ�С�����ÿɼ�
        frame.setBounds(200,200,500,500);
        frame.setVisible(true);
    }
    //������
    public static void main(String[] args)
    {
        new MyCalculator().init();
    }
}