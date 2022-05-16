/**
 * Classe che rappresenta la classe utilità
 * @author Gionata Donati
 */

package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Utils {

  List < Waypoint > myWaypoint = new ArrayList < Waypoint > ();
  List < List < Waypoint >> combination = new LinkedList < List < Waypoint >> ();
  List < List < Waypoint >> finale = new LinkedList < List < Waypoint >> ();

  public Utils() {

  }

  //TODO: Eccezzioni errori formato file
  public void importCupFile(String path) throws FileNotFoundException {
    File inFile = new File(
      path);
    Scanner inputFile = new Scanner(inFile);
    String str;
    String[] tokens;

    while (inputFile.hasNext()) {
      str = inputFile.nextLine();
      tokens = str.split(",");
      String code = tokens[1];
      String lat = String.format("%f", convertCupLatitude(tokens[3]));
      String lon = String.format("%f", convertCupLongitude(tokens[4]));
      String elev = tokens[5];

      Waypoint waypoint = new Waypoint(code, lat, lon, elev);
      myWaypoint.add(waypoint);

    }

    inputFile.close();
  }

  /**
   * This method calculates the distance between two points using the Haversine formula
   * 
   * @param lat1 The latitude of the first point
   * @param lat2 The latitude of the second point
   * @param lon1 The longitude of the first point
   * @param lon2 The longitude of the second point
   * @param el1 The elevation of the first point
   * @param el2 The elevation of the second point
   * @return The distance between the two points in kilometers
   */

  public static double distance(double lat1, double lat2, double lon1,
    double lon2, double el1, double el2) {

    final int R = 6371; // Radius of the earth

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
      Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters

    double height = el1 - el2;

    distance = Math.pow(distance, 2) + Math.pow(height, 2);

    return Math.sqrt(distance);
  }

  /**
   * Convertire formato latitudine dal file CUP a formato decimale 
   * 
   * @returns Formato decimale latitudine
   */

  public double convertCupLatitude(String latitude) {

    String degrees = latitude.substring(0, 2);

    String minutes = latitude.substring(2, 8);

    return Double.parseDouble(degrees) + Double.parseDouble(minutes) / 60;
  }

  /**
   * Convertire formato longitudine dal file CUP a formato decimale 
   * 
   * @returns Formato decimale longitudine
   */

  public double convertCupLongitude(String longitude) {

    String degrees = longitude.substring(0, 3);
    String minutes = longitude.substring(3, 9);
    return Double.parseDouble(degrees) + (Double.parseDouble(minutes) / 60);

  }

  /**
   * Stampa tutti gli oggetti di tipo Waypoint
   * 
   * @returns void
   */

  public void printWaypoint() {
    for (Waypoint waypoint: myWaypoint) {
      System.out.println(waypoint);
    }
  }

  /**
   * Returns a list of all combinations of a given list of values and size.
   * 
   * @param values the list of values to generate combinations from
   * @param size the number of values in each combination
   * @return a list of all combinations of the given values
   */

  public static List < List < Waypoint >> combinations(List < Waypoint > values, int size) {
    if (0 == size) {
      return Collections.singletonList(Collections. < Waypoint > emptyList());
    }

    if (values.isEmpty()) {
      return Collections.emptyList();
    }

    List < List < Waypoint >> combination = new LinkedList < List < Waypoint >> ();

    Waypoint actual = values.iterator().next();

    List < Waypoint > subSet = new LinkedList < Waypoint > (values);
    subSet.remove(actual);

    List < List < Waypoint >> subSetCombination = combinations(subSet, size - 1);

    for (List < Waypoint > set: subSetCombination) {
      List < Waypoint > newSet = new LinkedList < Waypoint > (set);
      newSet.add(0, actual);
      combination.add(newSet);
    }

    combination.addAll(combinations(subSet, size));

    return combination;
  }

  /**
   * This method calculates the combination for a group of 3
   */

  public void calculateCombinationForGroupOf3() {

    this.combination = Utils.combinations(this.myWaypoint, 3);

  }

  /**
   * Prints all combinations of 3 elements in the array.
   */

  public void printCombinationForGroupOf3() {
    for (int i = 0; i < this.combination.size(); i++) {
      System.out.println("Combinazione " + i);
      for (int j = 0; j < this.combination.get(i).size(); j++) {
        System.out.println(this.combination.get(i).get(j));
      }
    }
  }

  /**
   * This code is calculating the distance between three points and 
   * determining if the triangle they form is within certain parameters.
   */

  public void calculateTriangleLees500() {

    for (int i = 0; i < combination.size(); i++) {
      double distanceAb = 0;
      double distanceBc = 0;
      double distanceCa = 0;

      // This calculates the distance between points A and B

      distanceAb = distance(Double.parseDouble(combination.get(i).get(0).getLatitude()), Double.parseDouble(combination.get(i).get(1).getLatitude()), Double.parseDouble(combination.get(i).get(0).getLongitude()), Double.parseDouble(combination.get(i).get(1).getLongitude()), 0, 0) / 1000.00;

      // This calculates the distance between points B and C
      distanceBc = distance(Double.parseDouble(combination.get(i).get(1).getLatitude()), Double.parseDouble(combination.get(i).get(2).getLatitude()), Double.parseDouble(combination.get(i).get(1).getLongitude()), Double.parseDouble(combination.get(i).get(2).getLongitude()), 0, 0) / 1000.00;

      // This calculates the distance between points C and A
      distanceCa = distance(Double.parseDouble(combination.get(i).get(2).getLatitude()), Double.parseDouble(combination.get(i).get(0).getLatitude()), Double.parseDouble(combination.get(i).get(2).getLongitude()), Double.parseDouble(combination.get(i).get(0).getLongitude()), 0, 0) / 1000.00;

      // This calculates the total distance of the triangle

      double totalDistance = distanceAb + distanceBc + distanceCa;

      // This calculates the shortest leg of the triangle
      double shortestLeg = min(distanceAb, distanceBc, distanceCa);

      // This checks if the triangle meets the requirements
      // and if so, adds it to the list

      if (500 > totalDistance && shortestLeg > ((totalDistance) / 100) * 28) {
        finale.add(combination.get(i));
      }

    }

  }

  /**
   * This code is calculating the distance between three points and 
   * determining if the triangle they form is within certain parameters.
   */
  public void calculateTriangleMore500() {

    for (int i = 0; i < combination.size(); i++) {
      double distanceAb = 0;
      double distanceBc = 0;
      double distanceCa = 0;

      // This calculates the distance between points A and B
      distanceAb = distance(Double.parseDouble(combination.get(i).get(0).getLatitude()), Double.parseDouble(combination.get(i).get(1).getLatitude()), Double.parseDouble(combination.get(i).get(0).getLongitude()), Double.parseDouble(combination.get(i).get(1).getLongitude()), 0, 0) / 1000.00;

      // This calculates the distance between points B and C
      distanceBc = distance(Double.parseDouble(combination.get(i).get(1).getLatitude()), Double.parseDouble(combination.get(i).get(2).getLatitude()), Double.parseDouble(combination.get(i).get(1).getLongitude()), Double.parseDouble(combination.get(i).get(2).getLongitude()), 0, 0) / 1000.00;

      // This calculates the distance between points C and A
      distanceCa = distance(Double.parseDouble(combination.get(i).get(2).getLatitude()), Double.parseDouble(combination.get(i).get(0).getLatitude()), Double.parseDouble(combination.get(i).get(2).getLongitude()), Double.parseDouble(combination.get(i).get(0).getLongitude()), 0, 0) / 1000.00;

      // This calculates the total distance of the triangle
      double totalDistance = distanceAb + distanceBc + distanceCa;

      // This calculates the shortest leg of the triangle
      double shortestLeg = min(distanceAb, distanceBc, distanceCa);
      // This calculates the longest leg of the triangle
      double longestLeg = biggestOfThree(distanceAb, distanceBc, distanceCa);

      // This checks if the triangle meets the requirements
      // and if so, adds it to the list
      if (totalDistance >= 500 && shortestLeg > (totalDistance / 100) * 25 && longestLeg < (totalDistance) / 100 * 45) {
        finale.add(combination.get(i));
      }

    }

  }

  /**
   * This code returns the minimum value of 3 doubles
   * @param a first double
   * @param b second double
   * @param c third double
   * @return the minimum value of the 3 inputted doubles
   */

  public static double min(double a, double b, double c) {
    return Math.min(Math.min(a, b), c);
  }

  /**
   * Returns the largest of three numbers
   * 
   * @param x the first number
   * @param y the second number
   * @param z the third number
   * @return the largest number
   */

  static double biggestOfThree(double x, double y, double z) {

    return z > (x > y ? x : y) ? z : ((x > y) ? x : y);
  }

  /**
   * Prints the finale.
   */

  public void printFinale() {
    for (int i = 0; i < finale.size(); i++) {
      System.out.println("Combinazione " + i);
      for (int j = 0; j < finale.get(i).size(); j++) {
        System.out.println(finale.get(i).get(j));
      }
    }
  }

}