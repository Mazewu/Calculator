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
    //建立需要的各种组件
    String input = "";//显示在文本框中的字符串
    Frame frame = new Frame("计算器");//整体的窗口
    Button[] button = new Button[23];//需要的按钮
    TextField textField = new TextField(15);//文本框
    String[] name = {"sqrt","1/x","%","C","Del","7","8","9","+","-","4","5","6","*","/","1","2","3","(",")","0",".","="};//按钮的名称
    Panel p1 = new Panel();//装载文本域的容器
    Panel p2 = new Panel();//装载按钮的容器
    
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
        String s2 = ""; // 储存中间结果的Lists2

        //遍历传入的ls
        
        for (String item : str) {
            System.out.println(item);
            
            if (!m.isOperator(item)){
                s2 += " "+item;
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while(!s1.peek().equals("(")) {
                    s2 +=" "+s1.pop();
                }
                s1.pop();//!!! 将 ( 弹出 s1栈， 消除小括号
            }else if(item.equals("sqrt")||item.equals("1/x")){
                s2 += " "+item;
            }
            else {
                //当item的优先级小于或者等于   s1 栈顶的运算符
                // 将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                //编写一个Operate类，再编写getValue方法来获取当前运算符的优先级
                while (s1.size() != 0 && m.getValue(s1.peek()) >=m.getValue(item)){
                    //把s1中的运算符加入s2，直到，item的优先级大于s1栈顶的运算符,退出循环，并且将item压入s1
                    s2 += " "+s1.pop();
                }
                //还需要将item压入栈
                s1.push(item);
            }

        }
        //将s1中剩余的运算符依次弹出并加入s2
        while(s1.size() != 0) {
            s2 += " "+s1.pop();
        }

        return s2; //注意因为是存放到List, 因此按顺序输出就是对应的后缀表达式对应的List


    }
    //用栈来实现后缀表达式的计算
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
        //为Panel容器添加视图，并设置好布局键盘布局
        textField.setFont(new Font("宋体",Font.PLAIN,50));//设置字体格式
        textField.setEditable(false);
        //将文本域添加到p1容器中
        p1.add(textField);

        //设置按钮容器的布局
        p2.setLayout(new GridLayout(5,5,5,5));  

        

        //为每个button命名
        for(int i=0; i<23; i++)
        {
            button[i] = new Button(name[i]);
        }

        //设置按钮点击后的监听器
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String buttonname = e.getActionCommand();
                input += buttonname;

                if(buttonname.equals("+")||buttonname.equals("-")||buttonname.equals("*")||buttonname.equals("/")||buttonname.equals("(")||buttonname.equals(")")||buttonname.equals("%"))
                {
                    
                    input = input.substring(0, input.length() - 1);  //将这些符号去掉
                    
                    if(buttonname == "("){
                        input += buttonname+" ";
                    }else if(buttonname == ")"){
                        input +=" "+buttonname;
                    }else if(buttonname == "1/x"){
                        input = input.substring(0, input.length() - 2);
                        input +=" "+buttonname;
                    }
                    else{
                        input += " "+buttonname +" ";  //在数字后加空格再加符号再加空格，为了将符号在str中单独占一格
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
                //点击等于号的后的行为
                if(buttonname.equals("="))
                {
                    input = input.substring(0, input.length() - 1);
                    input = parseSuffixExpression(input);
                    String str="";
                    for(int i =1;i<input.length();i++){
                        str += input.charAt(i);
                    }
                    input = compute(str).toString();
                    textField.setText(input);  //输出计算结果

                }
                //点击清除键后的行为
                if(buttonname.equals("C"))
                {
                    input = "";
                }

                //点击退格键后的行为
                if(buttonname.equals("Del"))
                {
                    input = input.substring(0, input.length() - 3);  //先将输入的"Del"删去
                    if(input.endsWith(" "))        //如果input的后面跟的是" ",则input最后的输入是一个计算符号，删去符号和用以区分数字符号的" "
                        input = input.substring(0,input.length() - 3);
                    else if(input.endsWith("t")){
                        input = input.substring(0,input.length() - 5);
                    }
                    else if(input.endsWith("x")){
                        input = input.substring(0,input.length() - 4);
                    }
                    else                                          //删去一个数字          
                        input = input.substring(0, input.length() - 1);
                    
                }

                //得到结果后如果再点击其他键，就执行清除
                textField.setText(input);
            }
        };
        //将监听器装到每个按钮上
        for(int x = 0;x<23;x++)
        {
            button[x].addActionListener(listener);
        }
        //将按钮装到p2容器中，注意装完监听器后再将按钮装到容器中
        for(int x = 0;x<23;x++) 
        {
            p2.add(button[x]);
        }
        //组装容器到窗口上
        frame.add(p1,BorderLayout.NORTH);
        frame.add(p2);

        //设置frame窗口大小并设置可见
        frame.setBounds(200,200,500,500);
        frame.setVisible(true);
    }
    //主函数
    public static void main(String[] args)
    {
        new MyCalculator().init();
    }
}