import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.Iterator;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private EdgeWeightedDigraph DigrapghVertical;
    private EdgeWeightedDigraph DigrapghHorizontal;
    private int sourceVertical;
    private int sourceHorizontal;
    private int destinationVertical;
    private int destinationHorizontal;
    private double[][] energyTo;
    private int[][] xTo;


    private int intToX(int a) {
        return a % picture.width();
    }
    private int intToY(int a) {
        return a /picture.width();
    }

    private int xyTo1D(int x, int y) {
        return (y * picture.width()) + x;
    }
    //Unused in this context of the program but useful in performing other operations on the image
/*
    private void FillDigrapghVertical() {
        DigrapghVertical = new EdgeWeightedDigraph((picture.height()*picture.width())+4);
        sourceVertical = 1001;
        destinationVertical = 1002;

        for (int i = 0; i<energy[0].length;i++) {

            DirectedEdge VerticalSourceEdge = new DirectedEdge(sourceVertical,xyTo1D(0,i),energy[0][i]);
            DigrapghVertical.addEdge(VerticalSourceEdge);

            DirectedEdge VerticalDestinationEdge = new DirectedEdge(xyTo1D(i,energy[0].length-1),destinationVertical,energy[i][energy[0].length-1]);
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

            DirectedEdge HorizontalDestinationEdge = new DirectedEdge(xyTo1D(energy.length-1,i),destinationHorizontal,energy[energy.length-1][i]);
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
*/
    private boolean isPixel(int w, int h) {
        return w < picture.width() && h < picture.height();
    }

    private void fillEnergyArray () {
        energy = new double[picture.width()][picture.height()];
        for (int i = 0; i<picture.width();i++) {
            energy[i][0] = 1000.0;//first Column
            energy[i][energy[0].length-1] = 1000.0; // last Column
        }
        for (int i = 1; i < picture.height(); i++) {
            energy[0][i] = 1000.0; // Top Row
            energy[energy.length - 1][i] = 1000.0; // last Row
        }
        for (int k = 1; k < energy.length-1;k++) {
            for (int j = 1; j < energy[0].length-1;j++){
                Color pixelup = picture.get(k,j-1);
                Color pixeldown = picture.get(k,j+1);
                Color pixelleft = picture.get(k-1,j);
                Color pixelright = picture.get(k+1,j);

                double[] colorDifference = new double[6];

                colorDifference[0] = Math.abs(pixelleft.getRed() - pixelright.getRed());
                colorDifference[1] = Math.abs(pixelleft.getGreen() - pixelright.getGreen());
                colorDifference[2] = Math.abs(pixelleft.getBlue() - pixelright.getBlue());
                colorDifference[3] = Math.abs(pixelup.getRed() - pixeldown.getRed());
                colorDifference[4] = Math.abs(pixelup.getGreen() - pixeldown.getGreen());
                colorDifference[5] = Math.abs(pixelup.getBlue() - pixeldown.getBlue());

                double[] Delta = new double[2];
                for (int i = 0; i < 3; i++) {
                    Delta[0] += colorDifference[i]*colorDifference[i];
                }
                for (int i = 3; i < 6; i++) {
                    Delta[1] += colorDifference[i]*colorDifference[i];
                }
                double energyToFill = 0;
                for (int i = 0 ; i < 2; i++) {
                    energyToFill += Delta[i]*Delta[i];
                }
                energy[k][j] = Math.sqrt(energyToFill);
            }
        }
    }
    private void relax(int i, int j, int i1, int i2) {
        if (energyTo[i1][i2] > energyTo[i][j] + energy[i1][i2]) {
            energyTo[i1][i2] = energyTo[i][j] + energy[i1][i2];
            xTo[i1][i2] = i;
        }
    }
    private void transposeEnergyArray() {
        double[][] tranposedEnergy = new double[this.energy[0].length][this.energy.length];
        for (int i = 0; i < this.energy[0].length;i++) {
            for (int j = 0; j < this.energy.length; j++) {
                tranposedEnergy[i][j] = this.energy[j][i];
            }
        }
        this.energy = tranposedEnergy;
    }
    private void transposeImage () {
        Picture transpose = new Picture(this.picture.height(), this.picture.width());
        for (int i = 0; i < picture.height();i++) {
            for (int j = 0 ; j < picture.width();j++) {
                transpose.set(i,j,this.picture.get(j,i));
            }
        }
        this.picture = transpose;
    }
    public SeamCarver(Picture picture) {
        this.picture = picture;
        //setting the border of the energy array to 1000
        fillEnergyArray();

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
        transposeImage();
        transposeEnergyArray();
        int[] seam = findVerticalSeam();
        transposeImage();
        transposeEnergyArray();
        return seam;
    }              // sequence of indices for horizontal seam
    public   int[] findVerticalSeam() {
        energyTo = new double[picture.width()][picture.height()];
        xTo = new int[picture.width()][picture.height()];

        //set all the values in energyTo array as Maximum
        for (int i = 0; i < picture.width();i++) {
            for (int j = 0; j < picture.height(); j++) {
                energyTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        //setting the value of top row to be the highest
        for (int i = 0; i < picture.width();i++) {
            energyTo[i][0] = 99999;
        }
        //checking the bottom three columns for the shortest path on every pixel and updating energy there
        for (int j = 0; j< picture.height()-1;j++) {
            for (int i = 0; i< picture.width();i++) {
                if (i > 0) {
                    relax(i,j,i-1,j+1);
                }
                relax(i,j,i,j+1);

                if (i < picture.width()-2) {
                    relax(i,j,i+1,j+1);
                }
            }
        }

        //finding the lowest energy column in last row
        double minimumEnergy = Double.POSITIVE_INFINITY;
        int minEnergyX = -1;
        for (int w = 0; w < picture.width();w++) {
            if (energyTo[w][picture.height()-1] < minimumEnergy) {
                minEnergyX = w;
                minimumEnergy = energyTo[w][picture.height()-1];
            }
        }
        //checking if the minEnergyX does not remoan the same
        assert minEnergyX != -1;

        //backtracking and upgrading the seam with the values
        int[] seam = new int[picture.height()];
        seam[height()-1] = minEnergyX;
        int prevX = xTo[minEnergyX][height()-1];

        for (int h = picture.height()-2;h >=0;h--) {
            seam[h] = prevX;
            prevX = xTo[prevX][h];
        }
        return seam;
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
        transposeImage();
        transposeEnergyArray();
        removeVerticalSeam(seam);
        transposeImage();
        transposeEnergyArray();
    }   // remove horizontal seam from current picture
    public    void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (seam.length != picture.height() || picture.width() <= 1) {
         throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length-1; i++) {
            if (seam[i] >= picture.width() || Math.abs(seam[i]-seam[i+1]) > 1) {
                throw new IllegalArgumentException("Inappropriate numbers in seam array");
            }
        }
        Picture newPicture = new Picture(picture.width()-1,picture.height());

        for (int j = 0; j < picture.height(); j++) {
            for (int i = 0; i < seam[j];i++) {
                newPicture.set(i,j,this.picture.get(i,j));
            }
            for (int i = seam[j];i < newPicture.width();i++) {
                newPicture.set(i,j,this.picture.get(i+1,j));
            }
        }

        this.picture = newPicture;
        fillEnergyArray();

    }    // remove vertical seam from current picture
}