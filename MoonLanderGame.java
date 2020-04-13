package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed; 
    private boolean isGameStopped;
    private GameObject platform;
    private int score;
    
    private void createGame() {
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;
        createGameObjects();
        drawScene();
        setTurnTimer(50);
        score = 1000;
        
        
    }
    
    private void createGameObjects(){
        rocket = new Rocket((WIDTH / 2 - 5), 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
    }
    
    private void drawScene() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                setCellColor(x, y, Color.BLACK);

        landscape.draw(this);
        rocket.draw(this);
    }
    
    private void check() {
        if (rocket.isCollision(landscape) 
           && !(rocket.isCollision(platform)))  gameOver();
        
        if (rocket.isCollision(platform) 
           && rocket.isStopped())  win();
        
    }
    
    private void win() {
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "ПОБЕДА!", Color.RED, 70);
        stopTurnTimer();
    }
    
    private void gameOver() {
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Неудача!", Color.YELLOW, 50);
        stopTurnTimer();
        score = 0;
    }
    
    
    @Override
    public void onTurn(int turnOk) {
        if(score > 0) score--;
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        setScore(score); 
        drawScene();
    }
    
    @Override
    public void setCellColor(int x, int y, Color color)   {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
            super.setCellColor(x, y, color);  
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        showGrid(false);
    }
    
    @Override
    public void onKeyPress(Key key) {
        
        switch (key){
            case SPACE: 
                if (isGameStopped) createGame();
                break;
            case RIGHT: 
                isRightPressed = true;
                isLeftPressed = false;
                break;
            case LEFT: 
                isLeftPressed = true;
                isRightPressed = false;
                break;
             case UP: 
                 isUpPressed = true;    
        }

      } 
    
    @Override
    public void onKeyReleased(Key key) {
       if (key == Key.UP) isUpPressed = false;
       if (key == Key.LEFT) isLeftPressed = false;
       if (key == Key.RIGHT) isRightPressed = false;
   } 
    
}
