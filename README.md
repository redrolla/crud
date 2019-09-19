# crud
Learning Java+Git

File consists of lines, each line - information about a single ship, line looks like this:
[id int_8];[ship_name String_30];[ship_class String_16];[current_crew_capacity int_8];
[tonnage int_8];[destination String_16];[has_weapons? String_4]

Command examples:
    -c Salvation;Grand cruiser;4353;350000;Eye Of Terror;true
    -c Retribution;Grand cruiser;00003456;00350000;Holy Terra    ;true
    -c Harrower          ;Firestorm frigate ;00000246;00005367;Ultramar.Calth;true
    -c Golden Coin;Privateer frigate;114;6000;Ultramar.Maccrage;false

    -d 1
    -d 3

    -u 3;Salvation;Cruiser;00002353;00150000;Eye Of Terror;true


Task:
1. fileName recieved as a parameter, we will do CrUD operations with it
2. programm must read information from console,
    a) if information doesnt match with a pattern from "d)" - programm must write a warning in console and continue work
    b) programm must finish work when written "exit" in console
    c) information is separated using ";" in file
    e) parameters description:
        -c => create new line in file
        -u => update line
        -d => delete line

        id - must generate automaticly incrimenting highest existing id in file, 8 numbers, if length less than 8 - add 0s before to match 8
        ship_name - String, max 32 symbols, add spaces " " after if length is less
        ship_class - String max 24 symbols, add spaces " " after if length is less
        current_crew_capacity - 8 numbers, add 0 before to match length 8
        tonnage - 8 numbers, add 0s to match length 8
        destination - String, max 24 symbols
        has_weapons? - String value of boolean, length 5
