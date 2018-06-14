import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.AcyclicSP;
import java.awt.*;
import java.util.ArrayList;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private EdgeWeightedDigraph DigrapghVertical;
    private EdgeWeightedDigraph DigrapghHorizontal;
    private int sourceVertical;
    private int sourceHorizontal;
    private int destinationVertical;
    private int destinationHorizontal;


    private int intToX(int a) {
        return a % picture.width();
    }
    private int intToY(int a) {
        return a /picture.width();
    }

    private int xyTo1D(int x, int y) {
        return (y * picture.width()) + x;
    }

    private void FillDigrapghVertical() {
        DigrapghVertical = new EdgeWeightedDigraph((picture.height()*picture.width())+4);
        sourceVertical = 1001;
        destinationVertical = 1002;

        for (int i = 0; i<energy[0].length;i++) {

            DirectedEdge VerticalSourceEdge = new DirectedEdge(sourceVertical,xyTo1D(0,i),energy[0][i]);
            DigrapghVertical.addEdge(VerticalSourceEdge);

            DirectedEdge VerticalDestinationEdge = new DirectedEdge(xyTo1D(i,energy.length-1),destinationVertical,energy[i][energy.length-1]);
            DigrapghVertical.addEdge(VerticalDestinationEdge);
        }
        for (int i = 0; i < energy.length-1;i++) {
            for (int j = 1; j < energy[0].length-1; j++) {
                DigrapghVertical.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i+1,j-1),energy[i+1][j-1]));
                DigrapghVertical.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i+1,j),energy[i+1][j]));
                DigrapghVertical.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i+1,j+1),energy[i+1][j+1]));
            }
        }
    }private void FillDigrapghHorizontal() {
        DigrapghHorizontal = new EdgeWeightedDigraph((picture.height()*picture.width())+4);
        sourceHorizontal = 1003;
        destinationHorizontal = 1004;

        for (int i = 0; i<energy[0].length;i++) {
            DirectedEdge HorizontalSourceEdge = new DirectedEdge(sourceHorizontal,xyTo1D(i,0),energy[i][0]);
            DigrapghHorizontal.addEdge(HorizontalSourceEdge);

            DirectedEdge HorizontalDestinationEdge = new DirectedEdge(xyTo1D(energy[0].length-1,i),destinationHorizontal,energy[energy.length-1][i]);
            DigrapghHorizontal.addEdge(HorizontalDestinationEdge);
        }
        for (int i = 1; i < energy.length-1;i++) {
            for (int j = 0; j < energy[0].length-1; j++) {
                DigrapghHorizontal.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i-1,j+1),energy[i-1][j+1]));
                DigrapghHorizontal.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i,j+1),energy[i][j+1]));
                DigrapghHorizontal.addEdge(new DirectedEdge(xyTo1D(i,j),xyTo1D(i+1,j+1),energy[i+1][j+1]));
            }
        }
    }

    private boolean isPixel(int w, int h) {
        return w < picture.width() && h < picture.height();
    }

    private void fillEnergyArray () {
        energy = new double[picture.width()][picture.height()];
        for (int i = 0; i<energy[0].length;i++) {
            energy[0][i] = 1000.0; //Top Row
            energy[i][0] = 1000.0;//first Column
            energy[i][energy.length-1] = 1000.0; // last Column
            energy[energy[0].length][i] = 1000.0; // last Row
        }
        for (int i = 1; i < energy.length-1;i++) {
            for (int j = 1; j < energy[0].length-1;j++){
                Color pixelup = picture.get(i,j-1);
                Color pixeldown = picture.get(i,j+1);
                Color pixelleft = picture.get(i-1,j);
                Color pixelright = picture.get(i+1,j);

                double[] colorDifference = new double[6];

                colorDifference[0] = Math.abs(pixelleft.getRed() - pixelright.getRed());
                colorDifference[1] = Math.abs(pixelleft.getGreen() - pixelright.getGreen());
                colorDifference[2] = Math.abs(pixelleft.getBlue() - pixelright.getBlue());
                colorDifference[3] = Math.abs(pixelup.getRed() - pixeldown.getRed());
                colorDifference[4] = Math.abs(pixelup.getGreen() - pixeldown.getGreen());
                colorDifference[5] = Math.abs(pixelup.getBlue() - pixeldown.getBlue());

                double[] Delta = new double[2];
                for (i = 0; i < 3; i++) {
                    Delta[0] += colorDifference[i]*colorDifference[i];
                }
                for (i = 3; i < 6; i++) {
                    Delta[1] += colorDifference[i]*colorDifference[i];
                }
                double energyToFill = 0;
                for (i = 0 ; i < 2; i++) {
                    energyToFill += Delta[i]*Delta[i];
                }
                energy[i][j] = Math.sqrt(energyToFill);
            }
        }
    }
    public SeamCarver(Picture picture) {
        this.picture = picture;
        //setting the border of the energy array to 1000
        fillEnergyArray();
        FillDigrapghHorizontal();
        FillDigrapghVertical();

    }                // create a seam carver object based on the given picture
    public Picture picture() {
        return this.picture;
    }                         // current picture
    public     int width() {
        return picture.width();
    }                           // width of current picture
    public     int height() {
        return picture.height();
    }                          // height of current picture
    public  double energy(int x, int y) {
        if (!isPixel(x,y)) {
            throw new IndexOutOfBoundsException();
        }
        return energy[x][y];

    }              // energy of pixel at column x and row y
    public   int[] findHorizontalSeam() {
        AcyclicSP SP = new AcyclicSP(DigrapghHorizontal,sourceHorizontal);
        ArrayList<Integer> toReturn = new ArrayList<>();
        Iterable<DirectedEdge> path = SP.pathTo(destinationHorizontal);
        while (path.iterator().hasNext()) {
            toReturn.add(intToY(path.iterator().next().from()));
        }
        return (int[]) toReturn.toArray();
    }              // sequence of indices for horizontal seam
    public   int[] findVerticalSeam() {
        AcyclicSP SP = new AcyclicSP(DigrapghVertical,sourceVertical);
        ArrayList<Integer> toReturn = new ArrayList<>();
        Iterable<DirectedEdge> path = SP.pathTo(destinationVertical);
        while (path.iterator().hasNext()) {
            toReturn.add(intToY(path.iterator().next().from()));
        }
    }                // sequence of indices for vertical seam
    public    void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (seam.length != picture.width() || picture.height() <= 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length-1; i++) {
            if (seam[i] >= picture.width() || Math.abs(seam[i]-seam[i+1]) > 1 ) {
                throw new IllegalArgumentException();
            }

        }
    }   // remove horizontal seam from current picture
    public    void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (seam.length != picture.height() || picture.width() <= 1) {
         throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length-1; i++) {
            if (seam[i] >= picture.height() || Math.abs(seam[i]-seam[i+1]) > 1) {
                throw new IllegalArgumentException();
            }
        }

    }    // remove vertical seam from current picture
}