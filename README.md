# p2p-lab-tankhunter

The P2P Lab aims to understand P2P concepts while implementing a P2P multiplayer game. We designed a P2P GUI based game called as “Tank
Hunters”. The game provides an interface for multiple users which are connected through a network, to play a battle of Tanks where aim is to destroy opponent’s tank.

Game World Concept
===================

Tank Hunters has multiple regions (9 in our case), which are randomly loaded when a peer joins the game. Every region or cell is has different states like wall, grass, stones, etc. with some items like power ups and bonuses. These cells or Regions are managed by a single Region Controller. The peers while joining a game are assigned a region and hence get associated and controlled by Region Controller.

Game Design
===============
Tank hunters is a multiplayer 2D action game. In this game every player is in control of a tank which moves around in a single field. The goal of the player is to destroy maximum number of enemy tanks in a predetermined number of rounds or time.
