import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class project {
    int temp = 24;
    int datapoints = 6;
    double[][] pout;
    double[][] pin;
    double[] Hsat;
    double[] Ssat;
    double[] Tsat;
    double[] H01;
    double[] Hin;
    double[] Sin;
    double[] Sout;
    double pressure;
    double[] joulethomson;

    public project(String filename1, String filename2) throws Exception {
        File f1 = new File(filename1);
        BufferedReader b1 = new BufferedReader(new FileReader(f1));
        pout = new double[temp][datapoints];
        pin = new double[temp][datapoints];
        for (int i = 1; i < temp; i++) {
            String[] d1 = b1.readLine().split(" ");
            for (int j = 0; j < datapoints; j++) {
                pin[i][j] = Double.parseDouble(d1[j]);
            }

        }
        File f2 = new File(filename2);
        BufferedReader b2 = new BufferedReader(new FileReader(f2));
        for (int i = 1; i < temp; i++) {
            String[] d2 = b2.readLine().split(" ");
            for (int j = 0; j < datapoints; j++) {
                pout[i][j] = Double.parseDouble(d2[j]);
            }

        }
        Hsat = new double[6];
        Ssat = new double[6];
        Tsat = new double[3];
        Hsat[0] = 16770.10;
        Hsat[1] = 39202.70;
        Hsat[2] = 31450.90;
        Hsat[3] = 46891.00;
        Hsat[4] = 32665.20;
        Hsat[5] = 47338.30;
        Ssat[0] = 218.49;
        Ssat[1] = 300.76;
        Ssat[2] = 263.87;
        Ssat[3] = 305.74;
        Ssat[4] = 267.07;
        Ssat[5] = 306.13;
        Tsat[0] = 272.64;
        Tsat[1] = 368.76;
        Tsat[2] = 375.61;
        int m;
        Hin = new double[24];
        H01 = new double[24];
        joulethomson = new double[24];
        for (int i = 1; i < 24; i++) {
            joulethomson[i] = 1 / (pin[i][1] * pin[i][5]) * (-1 + pin[i][0] * pin[i][3] / (pin[i][4] * pin[i][2]))
                    * 1000;
        }
        if (filename1 == "p1.4.txt") {
            m = 18;
            pressure = 1.4;
        } else {
            m = 19;
            pressure = 1.6;
        }
        for (int k = 1; k < m; k++) {
            double s = 0;
            for (int i = k; i < m; i++) {
                double delt = pin[i + 1][0] - pin[i][0];
                s += (pin[i][5] + pin[i + 1][5]) * delt / 2;
            }
            if (filename1 == "p1.4.txt") {
                Hin[k] = Hsat[2] - s;
                Hin[m] = Hsat[2];
            } else {
                Hin[k] = Hsat[4] - s;
                Hin[m] = Hsat[4];
            }

        }
        for (int k = m + 2; k < 24; k++) {
            int s = 0;
            for (int i = m + 2; i < k; i++) {
                double delt = pin[i + 1][0] - pin[i][0];
                s += (pin[i][5] + pin[i + 1][5]) * delt / 2;
            }
            if (filename1 == "p1.4.txt") {
                Hin[k] = Hsat[3] + s;
                Hin[m + 1] = Hsat[3];
            } else {
                Hin[k] = Hsat[5] + s;
                Hin[m + 1] = Hsat[5];
            }
        }
        for (int k = 1; k < 9; k++) {
            double s = 0;
            for (int i = k; i < 9; i++) {
                double delt = pout[i + 1][0] - pout[i][0];
                s += (pout[i][5] + pout[i + 1][5]) * delt / 2;
            }
            H01[k] = Hsat[0] - s;
            H01[9] = Hsat[0];

        }
        for (int k = 11; k < 24; k++) {
            int s = 0;
            for (int i = 10; i < k; i++) {
                double delt = pout[i + 1][0] - pout[i][0];
                s += (pout[i][5] + pout[i + 1][5]) * delt / 2;
            }
            H01[k] = Hsat[1] + s;
            H01[10] = Hsat[1];

        }
        double Hinn;
        double T;
        Scanner in = new Scanner(System.in);
        System.out.println("Input temperature : ");
        double b = in.nextDouble();
        T = b;
        double jt = 0;
        if (T == pin[m][0]) {
            Scanner x = new Scanner(System.in);
            System.out.println("enter quality :");
            double q = x.nextDouble();

            Hinn = (1 - q) * Hin[m] + q * Hin[m + 1];
            jt = (1 - q) * joulethomson[m] + q * joulethomson[m + 1];
            x.close();
        } else if (T < pin[m][0]) {
            double k = 0;
            for (int i = 1; i < m + 1; i++) {
                double g = 1;
                for (int j = 1; j < m + 1; j++) {
                    if (i != j)
                        g = g * (T - pin[j][0]) / (pin[i][0] - pin[j][0]);
                    else
                        g = g * 1;
                }
                k += g * Hin[i];
                jt += g * joulethomson[i];
            }
            Hinn = k;
        } else {
            double k = 0;
            for (int i = m + 2; i < 24; i++) {
                double g = 1;
                for (int j = m + 2; j < 24; j++) {
                    if (i != j)
                        g = g * (T - pin[j][0]) / (pin[i][0] - pin[j][0]);
                    else
                        g = g * 1;
                }
                k = k + g * Hin[i];
                jt = jt + g * joulethomson[i];
            }
            Hinn = k;
        }
        if (Hinn <= H01[10] && Hinn >= H01[9]) {
            System.out.println("final temp: " + pout[9][0]);
            System.out.println("quality : " + (Hinn - H01[9]) / (H01[10] - H01[9]));
            System.out.println("joule thomson coefficient :" + jt);
            double T2 = (T - jt * (pressure - 0.1));
            System.out.println("Temperature using joule thomson coefficient :" + T2);
            System.out.println(" Mix vapour + liquid");
        } else if (Hinn < H01[9]) {
            double k = 0;
            for (int i = 1; i < 9; i++) {
                double g = 1;
                for (int j = 1; j < 9; j++) {
                    if (i != j)
                        g = g * (Hinn - H01[j]) / (H01[i] - H01[j]);
                    else
                        g = g * 1;

                }
                k += g * pout[i][0];
            }
            System.out.println("final temp = " + k);
            System.out.println("joule thomson coefficient :" + jt);
            double T2 = (T - jt * (pressure - 0.1));
            System.out.println("Temperature using joule thomson coefficient :" + T2);
            System.out.println("subcooled liquid");
        } else {
            double k = 0;
            for (int i = 10; i < 23; i++) {
                double g = 1;
                for (int j = 10; j < 23; j++) {
                    if (i != j)
                        g = g * (Hinn - H01[j]) / (H01[i] - H01[j]);
                    else
                        g = g * 1;

                }
                k = k + g * pout[i][0];
            }
            System.out.println("final temp = " + k);
            System.out.println("joule thomson coefficient :" + jt);
            double T2 = (T - jt * (pressure - 0.1));
            System.out.println("Temperature using joule thomson coefficient : " + T2);
            System.out.println("Superheated vapour");
        }
        b1.close();
        b2.close();
        in.close();
    }

    public double interpolation(double x, double p1, double p2, double d1, double d2) {
        return (d2 - d1) / (p2 - p1) * (x - p1) + d1;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Input Pressure : ");
        Double s = in.nextDouble();
        String f;
        if (s == 1.4)
            f = "p1.4.txt";
        else
            f = "p1.6.txt";

        project p = new project(f, "p0.1.txt");
        System.out.println(p);
        in.close();

    }
}
