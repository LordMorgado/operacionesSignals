package operaciones;

import java.awt.Color;
import static java.lang.System.exit;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.ui.TextAnchor;

public class Con {

    public void generateInitialGraph(discreteFunction F,String Name,String Not,int color)
    {
        JFreeChart chart = ChartFactory.createBarChart(Name, "N",Not,
        getDataSet(F), PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
        br.setMaximumBarWidth(.01); //Para adelgazar las barras y que aprezcan discretas
        if(color==0)
            br.setSeriesPaint(0, Color.green); //Color de las barras
        else
            br.setSeriesPaint(0, Color.red); //Color de las barras
        //Labels de las barras
        br.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        br.setItemLabelsVisible(true);
        br.setItemLabelPaint(Color.white);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, 
        TextAnchor.TOP_CENTER);
        br.setPositiveItemLabelPosition(position);
        //Para las lineas de X y Y
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.WHITE);
        categoryPlot.addRangeMarker(marker);
        //Lineas
        chart.getPlot().setBackgroundPaint( Color.GRAY ); //Color de fondo
        ((CategoryPlot)chart.getPlot()).setRangeGridlinePaint(Color.WHITE); //Color de la cuadricula
        ChartFrame frame = new ChartFrame("Funciones Iniciales", chart); //Frame donde se introduce la Grafica
        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
    
    public void getFinalFunction(double [] in)
    {
        String aux="";
        for(double d:in)
            aux+=d+",";
        JOptionPane.showMessageDialog(null,"{ "+ aux +" ]","Funcion Obtenida",JOptionPane.OK_OPTION);
    }
    
    public void generateGraph(discreteFunction F, String operacion)
    {
        JFreeChart chart = ChartFactory.createBarChart(operacion, "N"," Y ( N ) ",
        getDataSet(F), PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
        br.setMaximumBarWidth(.01); //Para adelgazar las barras y que aprezcan discretas
        br.setSeriesPaint(0, Color.blue); //Color de las barras
        br.setItemMargin(0);
        //Labels de las barras
        br.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        br.setItemLabelsVisible(true);
        br.setItemLabelPaint(Color.white);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, 
        TextAnchor.TOP_CENTER);
        br.setPositiveItemLabelPosition(position);
        //Para las lineas de X y Y
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.WHITE);
        categoryPlot.addRangeMarker(marker);
        //Lineas
        chart.getPlot().setBackgroundPaint( Color.DARK_GRAY ); //Color de fondo
        ((CategoryPlot)chart.getPlot()).setRangeGridlinePaint(Color.WHITE); //Color de la cuadricula
        ChartFrame frame = new ChartFrame("Practica Convolucion de Funciones", chart); //Frame donde se introduce la Grafica
        frame.pack();
        frame.setSize(700, 600);
        frame.setVisible(true);
        getFinalFunction(F.getValues());
    }
    
    private static CategoryDataset getDataSet(discreteFunction F) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int i,zero=F.getZeroPosition();
        double [] values = F.getValues();
        for(i=0;i<values.length;i++)
            dataset.addValue(values[i], "Y ( N )", ""+(i-zero) );
        return dataset;
    }
    
    public double[] matMult(double [][] aux1,double [] aux2)
    {
        int x,y,aux;
        double [] mult=new double[aux2.length];
        for(x=0;x<aux1.length;x++) //Mueve las filas
        {
            mult[x]=0;
            for(y=0;y<aux1.length;y++)//Mueve las columnas
                    mult[x]+=aux1[x][y]*aux2[y];
        }
        
        return mult;
    }

    public void printM(double[][]M)
    {
        for (double[] M1 : M) {
            for (int j = 0; j<M.length; j++) {
                System.out.print("\t" + M1[j]);
                if(j==M.length-1)
                    System.out.println();
            }
        }
        
    }

    public discreteFunction getFunction(String parameter)
    {
        int zero=0;
                String function= JOptionPane.showInputDialog(null,
                parameter+"\n Para ingresar el cero, agregue un * despues del valor deseado, cada valor va separado por comas\n"
                        + "Ejemplo: 1,3,4,5*,5,4,1","Tipo de Convolucion",JOptionPane.QUESTION_MESSAGE);
        StringTokenizer st = new StringTokenizer(function,",");
        int tokensize=st.countTokens();
        double arr[]= new double[tokensize];
        int i;
        String token;
        for(i=0;i<tokensize;i++){
            token=st.nextToken();
            if(token.endsWith("*")){
                zero=i;
                String zeroS = new String(token.substring(0, token.length()-1));
                arr[i]=Double.parseDouble(zeroS);
            }
            else
                arr[i]=Double.parseDouble(token);
        }
        
        return new discreteFunction(arr,zero);
    }

    public discreteFunction sumaF()
    {
        discreteFunction DF1 = getFunction("Ingrese la primer funcion discreta");
        double [] f1=DF1.getValues();
        discreteFunction DF2 = getFunction("Ingrese la seguda funcion discreta");
        double f2[] = DF2.getValues();
        int arraySize = f1.length+f2.length-1;
        double eMatrix[][] = new double[arraySize][arraySize];
        int i,j;
        for(i=0;i<arraySize;i++)
            if(i<f1.length)
                eMatrix[i][0]=f1[i];
            else
                eMatrix[i][0]=0;

        for(j=1;j<arraySize;j++)//Va moviendo por columna para el corrimiento
        {
            eMatrix[0][j]=eMatrix[arraySize-1][j-1];
            for(i=1;i<arraySize;i++)
                eMatrix[i][j]=eMatrix[i-1][j-1];
        }

        double [] sMatrix = new double[arraySize];
        for(i=0;i<arraySize;i++)
            if(i<f2.length)
                sMatrix[i]=f2[i];
            else
                sMatrix[i]=0;

        double [] res=matMult(eMatrix,sMatrix);

        System.out.println("Envolvente:");
        printM(eMatrix);
        System.out.println("Aux:");
        for(double d:sMatrix)
            System.out.print("\t"+d);
        System.out.println("");
        System.out.println("Res:");
        for(double d:res)
            System.out.print("\t"+d);
        System.out.println("");
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        generateInitialGraph(DF2,"Funcion 2"," H ( N )",1);
        return new discreteFunction(res,DF1.getZeroPosition()+DF2.getZeroPosition());
    }

    public discreteFunction reflejar()
    {
        discreteFunction DF1 = getFunction("Ingrese la funcion discreta");
        double [] f1 = DF1.getValues();
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        double [] f2 = new double[f1.length];
        for (int i = f1.length - 1; i >= 0; i--) {
            f2[f1.length - 1 - i] = f1[i];
        }
        return new discreteFunction(f2,DF1.getZeroPosition());
    }

    public discreteFunction amplificar(double ganancia)
    {
        discreteFunction DF1 = getFunction("Ingrese la funcion discreta");
        double [] f1 = DF1.getValues();
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        for (int i = 0; i < f1.length; i++) {
            f1[i] = ganancia * f1[i];
        }
        return new discreteFunction(f1,DF1.getZeroPosition());
    }

    public discreteFunction desplazaF(int desplazamiento)
    {
        discreteFunction DF1 = getFunction("Ingrese la funcion discreta");
        double [] f1 = DF1.getValues();

        System.out.println(DF1.getZeroPosition());
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        if(desplazamiento == 0) {
            return new discreteFunction(f1,DF1.getZeroPosition());
        }
        else if (desplazamiento > 0) {
            List<Double> newArray = new ArrayList<>();
            for (int i = 0; i < f1.length; i++) {
                newArray.add(f1[i]);
            }
            for (int i = 0; i < desplazamiento; i++) {
                newArray.add(0.0);
            }
            double[] arr = new double[newArray.size()];
            for (int i = 0; i < newArray.size(); i++) {
                arr[i] = newArray.get(i);
            }
            int zero = desplazamiento + DF1.getZeroPosition();
            return new discreteFunction(arr,zero);
        }
        else {
            List<Double> newArray = new ArrayList<>();
            for (int i = 0; i < (desplazamiento*-1); i++) {
                newArray.add(0.0);
            }
            int zero = desplazamiento + DF1.getZeroPosition() + newArray.size();
            System.out.println(zero);
            for (int i = 0; i < f1.length; i++) {
                newArray.add(f1[i]);
            }
            double[] arr = new double[newArray.size()];
            for (int i = 0; i < newArray.size(); i++) {
                arr[i] = newArray.get(i);
            }
            return new discreteFunction(arr,zero);
        }
    }

    public discreteFunction convF()
    {
        discreteFunction DF1 = getFunction("Ingrese la primer funcion discreta");
        double [] f1=DF1.getValues();
        discreteFunction DF2 = getFunction("Ingrese la seguda funcion discreta");
        double f2[] = DF2.getValues();
        int arraySize = f1.length+f2.length-1;
        double eMatrix[][] = new double[arraySize][arraySize];
        int i,j;
        for(i=0;i<arraySize;i++)
            if(i<f1.length)
                eMatrix[i][0]=f1[i];
            else
                eMatrix[i][0]=0;
        
        for(j=1;j<arraySize;j++)//Va moviendo por columna para el corrimiento
        {
            eMatrix[0][j]=eMatrix[arraySize-1][j-1];
            for(i=1;i<arraySize;i++)
                eMatrix[i][j]=eMatrix[i-1][j-1];
        }
        
        double [] sMatrix = new double[arraySize];
        for(i=0;i<arraySize;i++)
            if(i<f2.length)
                sMatrix[i]=f2[i];
            else
                sMatrix[i]=0;
        
        double [] res=matMult(eMatrix,sMatrix);
        
        System.out.println("Envolvente:");
        printM(eMatrix);
        System.out.println("Aux:");
        for(double d:sMatrix)
            System.out.print("\t"+d);
        System.out.println("");
        System.out.println("Res:");
        for(double d:res)
            System.out.print("\t"+d);
        System.out.println("");
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        generateInitialGraph(DF2,"Funcion 2"," H ( N )",1);
        return new discreteFunction(res,DF1.getZeroPosition()+DF2.getZeroPosition());
    }
    
    public discreteFunction prepareFuntion(discreteFunction O)
    {
        double initialValues[]=O.getValues();
        int zero = O.getZeroPosition();
        double preparedArray[] = new double [initialValues.length*3];
        int fixedZero=zero+initialValues.length;
        int i=0,j=0;
        while(i<preparedArray.length)
        {
            preparedArray[i]=initialValues[j];
            i++;
            if(j==initialValues.length-1)
                j=0;
            else
                j++;
        }
            
        return new discreteFunction(preparedArray,fixedZero);
    }
    
    public int calculateZeroPosition(int zero,int arralength)
    {
        while(zero>=arralength)
            zero-=arralength;
        return zero;
    }
    
    public double[]periodicSum(int Frequency,double []original)
    {
        int limit=original.length/Frequency+original.length%Frequency;
        double fixedArray [][] = new double[limit][Frequency];
        //Taking values from original array to the fixed one in order to add the cols
        int iterator=0,j,i;    
        for(i=0;i<limit;i++)
                for(j=0;j<Frequency;j++)
                {
                    if(iterator<original.length)
                    fixedArray[i][j]=original[iterator];
                    else
                        fixedArray[i][j]=0;
                    iterator++;
                }
        double [] res = new double[Frequency];
        for(i=0;i<Frequency;i++)
            for(j=0;j<limit;j++)
                res[i]+=fixedArray[j][i];
        return res;
    }
    
    public discreteFunction convS()
    {
        int i,j;
        discreteFunction DF1 = getFunction("Ingrese la funcion discreta periodica");
        double [] f1=DF1.getValues();
        discreteFunction DF2 = getFunction("Ingrese la funcion discreta");
        double [] f2=DF2.getValues();
        int arraySize =f1.length+f2.length-1;
        
        double eMatrix[][] = new double[arraySize][arraySize]; //Rellena de ceros
        for(i=0;i<arraySize;i++)
            if(i<f1.length)
                eMatrix[i][0]=f1[i];
            else
                eMatrix[i][0]=0;
        
        for(j=1;j<arraySize;j++)//Va moviendo por columna para el corrimiento
        {
            eMatrix[0][j]=eMatrix[arraySize-1][j-1];
            for(i=1;i<arraySize;i++)
                eMatrix[i][j]=eMatrix[i-1][j-1];
        }
        printM(eMatrix);
        
        double [] sMatrix = new double[arraySize];
        for(i=0;i<arraySize;i++)
            if(i<f2.length)
                sMatrix[i]=f2[i];
            else
                sMatrix[i]=0;
        
        double [] aux=matMult(eMatrix,sMatrix);
        for(double r:sMatrix)
            System.out.print("\t"+r+"\t");
        
        System.out.print(".....");
        for(double r:aux)
            System.out.print("\t"+r+"\t");
        
        double []res= periodicSum(f1.length,aux);
        System.out.print(".....");
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        generateInitialGraph(DF2,"Funcion 2"," H ( N )",1);
        return new discreteFunction(res,DF1.getZeroPosition()+DF2.getZeroPosition());
    }
    
    public discreteFunction convT()
    {
        discreteFunction DF1 = getFunction("Ingrese la funcion discreta periodica");
        double []f1=DF1.getValues();
        discreteFunction DF2 = getFunction("Ingrese la seguda funcion discreta periodica");
        double [] f2 = DF2.getValues();
        int maxFreq;
        if(f1.length>f2.length)
            maxFreq=f1.length;
        else
            maxFreq=f2.length;
        
        double[][] eMatrix= new double[maxFreq][maxFreq];
        int i,j;
        for(i=0;i<maxFreq;i++)
            if(i<f1.length)
                eMatrix[i][0]=f1[i];
            else
                eMatrix[i][0]=0;
        for(j=1;j<maxFreq;j++)//Va moviendo por columna para el corrimiento
        {
            eMatrix[0][j]=eMatrix[maxFreq-1][j-1];
            for(i=1;i<maxFreq;i++)
                eMatrix[i][j]=eMatrix[i-1][j-1];
        }
        
        double [] sMatrix = new double[maxFreq];
        for(i=0;i<maxFreq;i++)
            if(i<f2.length)
                sMatrix[i]=f2[i];
            else
                sMatrix[i]=0;
        
        double [] res=matMult(eMatrix,sMatrix);
                System.out.println("Envolvente:");
        printM(eMatrix);
        System.out.println("Aux:");
        for(double d:sMatrix)
            System.out.print("\t"+d);
        System.out.println("");
        System.out.println("Res:");
        for(double d:res)
            System.out.print("\t"+d);
        System.out.println("");
        generateInitialGraph(DF1,"Funcion 1","X ( N )",0);
        generateInitialGraph(DF2,"Funcion 2"," H ( N )",1);
        return new discreteFunction(res,DF1.getZeroPosition()+DF2.getZeroPosition());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Con c= new Con();
                String option= JOptionPane.showInputDialog(null,
                        "1 - Suma/Resta de señales finitas \n"
                        + "2 - Amplificacion/atenuacion de señal finita y periodica \n"
                        + "3 - Reflejo de señal finita\n"
                        + "4 - desplazamiento de una señal finita\n"
                        + "6 - Convolucion de señales finitas\n"
                        + "Otro - Salir","Tipo de Convolucion",JOptionPane.QUESTION_MESSAGE);
        discreteFunction R;
        switch(Integer.parseInt(option))
        {
            /**
             * AMPLIFICACION
             */
            case 2:
                R=c.amplificar(Double.parseDouble(
                        JOptionPane.showInputDialog(
                                null,
                                "Introduce la ganancia g \n (g > 1 AMPLIFICAR; g < 1 ATENUAR)",
                                JOptionPane.QUESTION_MESSAGE)
                ));
                c.generateGraph(R, "Amplificacion/Atenuacion");
                break;
            /**
             * REFLEXION
             */
            case 3:
                R=c.reflejar();
                c.generateGraph(R, "Reflexion");
                break;
            /**
             *  DESPLAZAMIENTO
             */
            case 4:
                R=c.desplazaF(Integer.parseInt(
                        JOptionPane.showInputDialog(
                                null,
                                "Introduce el desplzamiento",
                                JOptionPane.QUESTION_MESSAGE)
                ));
                c.generateGraph(R, "Desplazamiento");
                break;
            /**
             * CONVOLUCION
             */
            case 6:
                R = c.convF();
                c.generateGraph(R, "Convolucion");
                break;
            default:
                exit(0);
                break;
        }
    }
    
}
