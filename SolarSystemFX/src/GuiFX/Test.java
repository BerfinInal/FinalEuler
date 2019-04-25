package GuiFX;


import javafx.animation.KeyFrame;
import javafx.application.Application;

import javafx.scene.Camera;
import javafx.scene.Group;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.net.URL;

public class Test extends Application {
    private CelestialBody[] planet = new CelestialBody[9];

    private static final float WIDTH = 1400;
    private static final float HEIGHT = 800;

    Group world = new Group();
    Group Moon = new Group();
    Group MoonGhost = new Group();

    private Sphere[] pl;
    private Sphere[] follow;

    @Override
    public void start(Stage primaryStage) {

        planet[0] = new CelestialBody("Sun", 0, 0, 0, 0, 0, 0, RealMasses.SUN_MASS);
        planet[1] = new CelestialBody("Mercury", RealDistance.mercuryXDistance, RealDistance.mercuryYDistance,
                RealDistance.mercuryZDistance, RealVelocities.mercuryXVel, RealVelocities.mercuryYVel,
                RealVelocities.mercuryZVel, RealMasses.MERCURY_MASS);
        planet[2] = new CelestialBody("Venus", RealDistance.venusXDistance, RealDistance.venusYDistance,
                RealDistance.venusZDistance, RealVelocities.venusXVel, RealVelocities.venusYVel,
                RealVelocities.venusZVel, RealMasses.VENUS_MASS);
        planet[3] = new CelestialBody("Earth", RealDistance.earthXDistance, RealDistance.earthYDistance,
                RealDistance.earthZDistance, RealVelocities.earthXVel, RealVelocities.earthYVel,
                RealVelocities.earthZVel, RealMasses.EARTH_MASS);
        planet[4] = new CelestialBody("Mars", RealDistance.marsXDistance, RealDistance.marsYDistance,
                RealDistance.marsZDistance, RealVelocities.marsXVel, RealVelocities.marsYVel, RealVelocities.marsZVel,
                RealMasses.MARS_MASS);
        planet[5] = new CelestialBody("Jupiter", RealDistance.jupiterXDistance, RealDistance.jupiterYDistance,
                RealDistance.jupiterZDistance, RealVelocities.jupiterXVel, RealVelocities.jupiterYVel,
                RealVelocities.jupiterZVel, RealMasses.JUPITER_MASS);
        planet[6] = new CelestialBody("Saturn", RealDistance.saturneXDistance, RealDistance.saturneYDistance,
                RealDistance.saturneZDistance, RealVelocities.saturnXVel, RealVelocities.saturnYVel,
                RealVelocities.saturnZVel, RealMasses.SATURNE_MASS);
        planet[7] = new CelestialBody("Uranus", RealDistance.uranusXDistance, RealDistance.uranusYDistance,
                RealDistance.uranusZDistance, RealVelocities.uranusXVel, RealVelocities.uranusYVel,
                RealVelocities.uranusZVel, RealMasses.URANUS_MASS);
        planet[8] = new CelestialBody("Neptune", RealDistance.neptuneXDistance, RealDistance.neptuneYDistance,
                RealDistance.neptuneZDistance, RealVelocities.neptuneXVel, RealVelocities.neptuneYVel,
                RealVelocities.neptuneZVel, RealMasses.NEPTUNE_MASS);


        PhongMaterial mercuryMaterial = new PhongMaterial();
        mercuryMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/mercury.jpeg")));

        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/sun.jpeg")));

        PhongMaterial venusMaterial = new PhongMaterial();
        venusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/venus.jpeg")));

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/earth/earth.normal.jpeg")));

        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/mars.jpeg")));

        PhongMaterial jupMaterial = new PhongMaterial();
        jupMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/jupiter.jpeg")));

        pl = new Sphere[planet.length];

        for (int i = 0; i < pl.length; i++) {

            pl[i] = new Sphere();
            pl[i].setTranslateX(planet[i].getXPosition() / 1000000000);
            pl[i].setTranslateY(planet[i].getYPosition() / 1000000000);
            pl[i].setTranslateZ(planet[i].getZPosition() / 1000000000);
            switch(i) {
                case 0:
                    pl[i].setMaterial(sunMaterial);
                    pl[i].setRadius(25);
                    break;
                case 1:
                    pl[i].setMaterial(mercuryMaterial);
                    pl[i].setRadius(7.5);
                    break;
                case 2:
                    pl[i].setMaterial(venusMaterial);
                    pl[i].setRadius(10);
                    break;
                case 3:
                    pl[i].setMaterial(earthMaterial);
                    pl[i].setRadius(12.5);
                    break;
                case 4:
                    pl[i].setMaterial(marsMaterial);
                    pl[i].setRadius(10);
                    break;
                case 5:
                    pl[i].setMaterial(jupMaterial);
                    pl[i].setRadius(40);
                    break;
                }

            world.getChildren().add(pl[i]);
        }

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);


        Slider slider = prepareSlider();
        world.translateZProperty().bind(slider.valueProperty());
        Moon.translateZProperty().bind(slider.valueProperty());


        //URL resource = getClass().getResource("travis.mp3");
        //Media media = new Media(resource.toString());

        //MediaPlayer player = new MediaPlayer(media);
        //player.play();

        Group root = new Group();
        root.getChildren().add(prepareImageView());
        root.getChildren().add(MoonGhost);
        root.getChildren().add(world);
        root.getChildren().add(Moon);
        root.getChildren().add(slider);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        //scene.getStylesheets().add("flatterAdd.css");
        //initMouseControl(world, scene, primaryStage);

        primaryStage.setTitle("Galaxy");
        primaryStage.setScene(scene);
        primaryStage.show();

        prepareAnimation();
    

    }

    private void prepareAnimation() {

        planet[0] = new CelestialBody("Sun", 0, 0, 0, 0, 0, 0, RealMasses.SUN_MASS);
        planet[1] = new CelestialBody("Mercury", RealDistance.mercuryXDistance, RealDistance.mercuryYDistance,
                RealDistance.mercuryZDistance, RealVelocities.mercuryXVel, RealVelocities.mercuryYVel,
                RealVelocities.mercuryZVel, RealMasses.MERCURY_MASS);
        planet[2] = new CelestialBody("Venus", RealDistance.venusXDistance, RealDistance.venusYDistance,
                RealDistance.venusZDistance, RealVelocities.venusXVel, RealVelocities.venusYVel,
                RealVelocities.venusZVel, RealMasses.VENUS_MASS);
        planet[3] = new CelestialBody("Earth", RealDistance.earthXDistance, RealDistance.earthYDistance,
                RealDistance.earthZDistance, RealVelocities.earthXVel, RealVelocities.earthYVel,
                RealVelocities.earthZVel, RealMasses.EARTH_MASS);
        planet[4] = new CelestialBody("Mars", RealDistance.marsXDistance, RealDistance.marsYDistance,
                RealDistance.marsZDistance, RealVelocities.marsXVel, RealVelocities.marsYVel, RealVelocities.marsZVel,
                RealMasses.MARS_MASS);
        planet[5] = new CelestialBody("Jupiter", RealDistance.jupiterXDistance, RealDistance.jupiterYDistance,
                RealDistance.jupiterZDistance, RealVelocities.jupiterXVel, RealVelocities.jupiterYVel,
                RealVelocities.jupiterZVel, RealMasses.JUPITER_MASS);
        planet[6] = new CelestialBody("Saturn", RealDistance.saturneXDistance, RealDistance.saturneYDistance,
                RealDistance.saturneZDistance, RealVelocities.saturnXVel, RealVelocities.saturnYVel,
                RealVelocities.saturnZVel, RealMasses.SATURNE_MASS);
        planet[7] = new CelestialBody("Uranus", RealDistance.uranusXDistance, RealDistance.uranusYDistance,
                RealDistance.uranusZDistance, RealVelocities.uranusXVel, RealVelocities.uranusYVel,
                RealVelocities.uranusZVel, RealMasses.URANUS_MASS);
        planet[8] = new CelestialBody("Neptune", RealDistance.neptuneXDistance, RealDistance.neptuneYDistance,
                RealDistance.neptuneZDistance, RealVelocities.neptuneXVel, RealVelocities.neptuneYVel,
                RealVelocities.neptuneZVel, RealMasses.NEPTUNE_MASS);


        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(10), e -> {
                    for (int i = 0; i < planet.length; i++) {

                        planet[i].computeGravityStep(planet, 2160);
                        redraw();

                    }
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();


    }

    //Background
    private ImageView prepareImageView() {
        Image image = new Image(Test.class.getResourceAsStream("galaxy.jpeg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.getTransforms().add(new Translate(-image.getWidth() / 2, -image.getHeight() / 2, 800));
        return imageView;
    }

    //Slider for Zoom
    private Slider prepareSlider() {
        Slider slider = new Slider();
        slider.setMax(800);
        slider.setMin(-400);
        slider.setPrefWidth(300d);
        slider.setLayoutX(-150);
        slider.setLayoutY(200);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setTranslateZ(5);
        slider.setStyle("-fx-base: black");
        return slider;
    }

    private void redraw() {

        follow = new Sphere[planet.length];

        for (int i = 0; i < pl.length; i++) {
            follow[i]  = new Sphere();
            follow[i].setRadius(2);

            follow[i].setTranslateX(planet[i].getXPosition() / 1000000000);
            follow[i].setTranslateY(planet[i].getYPosition() / 1000000000);
            follow[i].setTranslateZ(planet[i].getZPosition() / 1000000000);

            pl[i].setTranslateX(planet[i].getXPosition() / 1000000000);
            pl[i].setTranslateY(planet[i].getYPosition() / 1000000000);
            pl[i].setTranslateZ(planet[i].getZPosition() / 1000000000);

           // world.getChildren().add(follow[i]);

        }
    }
}

