# :christmas_tree: Advent of Code 2023

![GitHub](https://img.shields.io/badge/stars-50%2F50-yellow)

## What is Advent of Code?

_Excerpt from the Advent of Code [website](https://adventofcode.com/2020/about);_

    "Advent of Code is an Advent calendar of small programming puzzles for a variety of skill sets
    and skill levels that can be solved in any programming language you like. People use them as a
    speed contest, interview prep, company training, university coursework, practice problems, or
    to challenge each other."

## Contents
* [Getting Started](#getting-started)
* [The Days](#the-days)
    * [The Most Fun](#the-most-fun)
    * [The Most Interesting](#the-most-interesting)
    * [The Most Challenging](#the-most-challenging)
* [Answer Table](#answer-table)
* [Advent Calendar](#advent-calendar)

## Getting Started
Simply clone or download the repository into your local environment and import it as a Gradle project in your IDE.
Running [Solutions.kt](https://git.io/JII6v) will run the parts from all the completed days, reporting all the
answers and runtimes in the console.

### Gradle Tasks
| Task      | Description                                                                                        |
|-----------|----------------------------------------------------------------------------------------------------|
| `test`    | Runs the unit tests with coverage for the `implementation`, `solutions` and `common` sub-projects. |
| `detekt`  | Runs DeteKT with the custom configuration rules defined in detekt-config.yml.                      |

### IntelliJ Run Configurations
The `.run` directory contains XML configuration files from IntelliJ. Included are configurations for running the unit
tests in the `common`, `implementation` and `solutions` Gradle sub-projects as well as for each specific day.

## The Days

### The Most Fun
### The Most Interesting
### The Most Challenging

## Answer Table

| Day | Part 1 Answer | Avg Time | Part 2 Answer   | Avg Time | Documentation                                    |
|-----|---------------|----------|-----------------|----------|--------------------------------------------------|
| 01  | 55712         | 11ms     | 55413           | 12ms     | [Trebuchet?!](docs/DAY01.MD)                     |
| 02  | 2632          | 934μs    | 69629           | 233μs    | [Cube Conundrum](docs/DAY02.MD)                  |
| 03  | 531561        | 8ms      | 83279367        | 36ms     | [Gear Ratios](docs/DAY03.MD)                     |
| 04  | 27454         | 253μs    | 6857330         | 444ms    | [Scratchcards](docs/DAY04.MD)                    |
| 05  | 388071289     | 2ms      | 84206669        | -        | [If You Give A Seed A Fertilizer](docs/DAY05.MD) |
| 06  | 1413720       | 627μs    | 30565288        | 787ms    | [Wait For It](docs/DAY06.MD)                     |
| 07  | 250232501     | 15ms     | 249138943       | 2s 786ms | [Camel Cards](docs/DAY07.MD)                     |
| 08  | 12169         | 1ms      | 12030780859469  | 31ms     | [Haunted Wasteland](docs/DAY08.MD)               |
| 09  | 2098530125    | 4ms      | 1016            | 2ms      | [Mirage Maintenance](docs/DAY09.MD)              |
| 10  | 6856          | 4μs      | 501             | 799ms    | [Pipe Maze](docs/DAY10.MD)                       |
| 11  | 10292708      | 25ms     | 790194712336    | 19ms     | [Cosmic Expansion](docs/DAY11.MD)                |
| 12  | 7350          | 13ms     | 200097286528151 | 109ms    | [Hot Springs](docs/DAY12.MD)                     |
| 13  | -             | -        | -               | -        | [](docs/DAY13.MD)                                |
| 14  | -             | -        | -               | -        | [](docs/DAY14.MD)                                |
| 15  | 516070        | 676μs    | 244981          | 3ms      | [Lens Library](docs/DAY15.MD)                    |
| 16  | 6994          | 32ms     | 7488            | 1s 499ms | [The Floor Will Be Lava](docs/DAY16.MD)          |
| 17  | 698           | 1s 4ms   | 825             | 4s 52ms  | [Clumsy Crucible](docs/DAY17.MD)                 |
| 18  | 26857         | 1ms      | 129373230496292 | 246μs    | [Lavaduct Lagoon](docs/DAY18.MD)                 |
| 19  | -             | -        | -               | -        | [](docs/DAY19.MD)                                |
| 20  | 684125385     | 26ms     | 22803499706691  | 34ms     | [Pulse Propagation](docs/DAY20.MD)               |
| 21  | -             | -        | -               | -        | [](docs/DAY21.MD)                                |
| 22  | 386           | 430μs    | 39933           | 69ms     | [Sand Slabs](docs/DAY22.MD)                      |
| 23  | 2318          | 372ms    | 6426            | 9s 115ms | [A Long Walk](docs/DAY23.MD)                     |
| 24  | 31208         | 31ms     | 580043851566574 | 1s 179ms | [Never Tell Me The Odds](docs/DAY24.MD)          |
| 25  | 555702        | 2s 109ms | 49 Stars        | 2μs      | [Snowverload](docs/DAY25.MD)                     |

Average Execution Time: 838ms \
Total Execution Time: 12s 582ms \
Mac M2 Pro - OpenJDK Runtime Environment Corretto-18.0.2.9.1 - MacOS Sonoma 14.1.2

## Advent Calendar
A .GIF of the complete animated calendar goes here once completed.