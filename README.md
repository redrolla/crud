# crud
Learning Java+Git

Task:
[id int_8];[ship_name String_30];[ship_class String_16];[current_crew_capacity int_8];
[tonnage int_8];[destination String_16];[has_weapons? String_4]
1. fileName recieved as a parameter, we will do CrUD operations with it
2. programm must read information from console,
    a) if information doesnt match with a pattern from "d)" - programm must write a warning in console and continue work
    b) programm must end if entered "exit" in console
    c) information is separated using ";" in file
    d) information is a @String of following type:
        -c ship_name ship_class current_crew_capacity tonnage destination has_weapons?
        -u id ship_name ship_class current_crew_capacity tonnage destination has_weapons?
        -d id
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
