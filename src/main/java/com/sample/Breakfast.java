package com.sample;

import java.util.List;
import java.util.Objects;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Breakfast {

    public static int GoOutside = 0;

    public static int StayInOffice = 1;

    public static int Sunny = 0;

    public static int Rainy = 1;

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-eatWhat");
            Breakfast food = new Breakfast().weather(Breakfast.Sunny).memberCount(2).location(Breakfast.StayInOffice) ;
            kSession.insert(food);
            kSession.fireAllRules();
        } catch (Throwable t) {
            System.out.println("Found an error");
            t.printStackTrace();
        }
    }

    public Breakfast() {
    }

    public Breakfast(int location, int memberCount, String restaurant, List<String> menus, int weather, int vehicle, List<String> orders) {
        this.location = location;
        this.memberCount = memberCount;
        this.restaurant = restaurant;
        this.menus = menus;
        this.weather = weather;
        this.vehicle = vehicle;
        this.orders = orders;
    }

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getMemberCount() {
        return this.memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public List<String> getMenus() {
        return this.menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public int getWeather() {
        return this.weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public List<String> getOrders() {
        return this.orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public Breakfast location(int location) {
        setLocation(location);
        return this;
    }

    public Breakfast memberCount(int memberCount) {
        setMemberCount(memberCount);
        return this;
    }

    public Breakfast restaurant(String restaurant) {
        setRestaurant(restaurant);
        return this;
    }

    public Breakfast menus(List<String> menus) {
        setMenus(menus);
        return this;
    }

    public Breakfast weather(int weather) {
        setWeather(weather);
        return this;
    }

    public Breakfast vehicle(int vehicle) {
        setVehicle(vehicle);
        return this;
    }

    public Breakfast orders(List<String> orders) {
        setOrders(orders);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Breakfast)) {
            return false;
        }
        Breakfast breakfast = (Breakfast) o;
        return location == breakfast.location && memberCount == breakfast.memberCount && Objects.equals(restaurant, breakfast.restaurant) && Objects.equals(menus, breakfast.menus) && weather == breakfast.weather && vehicle == breakfast.vehicle && Objects.equals(orders, breakfast.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, memberCount, restaurant, menus, weather, vehicle, orders);
    }

    @Override
    public String toString() {
        return "{" +
            " location='" + getLocation() + "'" +
            ", memberCount='" + getMemberCount() + "'" +
            ", restaurant='" + getRestaurant() + "'" +
            ", menus='" + getMenus() + "'" +
            ", weather='" + getWeather() + "'" +
            ", vehicle='" + getVehicle() + "'" +
            ", orders='" + getOrders() + "'" +
            "}";
    }

    private int location;

    private int memberCount;

    private String restaurant;

    private List<String> menus;

    private int weather;

    private int vehicle;

    private List<String> orders;
}
