# Take It Easy - Java Implementation

## The game

This is a simplified, single-player version of the board game **"Take It Easy"** by Peter Burley, written in **Java**. The project follows the **Model-View-Controller (MVC) pattern**, commonly used for developing user interfaces that divides the related program logic into three interconnected elements.

This version follows the original **Take It Easy** rules: tiles are placed without rotation (in the board game, tiles have to be placed so the numbers displayed on them remain upright). The board consists of **19 hexagonal slots** where the player places drawn tiles. The goal is to align paths to maximize points.

---

## Start the game

### Prerequisites
Make sure you have at least **Java 21** and at least **Gradle 8.0** installed on your system.

### Clone the Repository
```sh
 git clone https://github.com/FlaviaDS/TakeItEasy.git
 cd TakeItEasy
```

### Run the Game
#### Windows
```sh
 ./gradlew.bat :launcher:run
```
#### Linux and macOS
```sh
 ./gradlew :launcher:run
```

At the start, the launcher initiates the game

<p align="center">
  <img src="Lancher.png" alt="Launcher" width="300">
</p>

When clicked upon, the Start button brings the player to this screen with the empy board and the first available tile ready to be placed.

<p align="center">
  <img src="StartGame.png" alt="Board" width="900">
</p>

---

## Rules
- Each turn, a **random tile** is drawn from a **deck of 27 tiles**.
- The player must **place the tile** in one of the 19 empty hexagonal slots, at his/her choice.
- Tiles are placed as drawn.
- Repeat until the **board is full**.
- The **score** is calculated based on complete paths sharing the same number.

### How Scoring Works
- A **complete path** (vertical, diagonal-left, diagonal-right) **earns points** (i.e., the line made of same numbers must run from one edge of the board to the opposite one).
- The score of a path = **path number × number of tiles in that path**.
- If a path contains a mismatching number, it scores 0 points.
Example: If a **completed path** of **5 tiles** contains the number **8**, the path earns **8 × 5 = 40 points**.

<p align="center">
  <img src="Score.png" width="900">
</p>

In the image, the score is 7 x 3 + 1 x 3 + 5 x 4 = 44.

---

## User Interface (UI) Controls
The game uses **Swing** for a simple GUI:
- The **board** consists of **19 hexagonal slots**.
- The **next tile** is displayed **before placement**.
- Click a **valid empty slot** to place the tile.
- The game **ends when the board is full**, showing the final score.

---

## Repository Structure

```
TakeItEasy
│
├── launcher/
│   ├── src/main/java/org/example/
│   │   ├── Launcher.java
│   ├── build.gradle.kts
│
├── hexagonal/
│   ├── src/main/java/org/example/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── view/
│   │   ├── utils/
│   ├── src/test/java/org/example/
│   │   ├── HexagonalGameBoardTest.java
│   ├── build.gradle.kts
│
├── .github/workflows/
├── .gradle/wrapper/
├── .idea/
├── .gitignore
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── quodana.yaml
└── README.md

```

---

## Development
### Design Pattern
The project follows the **MVC Pattern**:
- **Model** → Defines the game board, tiles, and score calculation.
- **View** → Handles the **Swing GUI**.
- **Controller** → Manages **game logic & input handling**.

### Technologies Used
- **Java 23.0.1**
- **Gradle 8.10** (build automation)
- **JUnit 5** (unit testing)
- **Jackson** (JSON parsing for tiles)
- **Swing** (graphical user interface)

### Dependencies (Gradle)
```gradle
dependencies {
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    testImplementation (platform("org.junit:junit-bom:5.10.0"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
}
```

---

## License
This project is licensed under the **MIT License**. It is for educational purposes only and does not intend to infringe any copyrights of the original game.

---

## Contributors
- **Flavia De Santis**

---

## Future Improvements
- **Multiplayer mode**
- **More UI improvements**
