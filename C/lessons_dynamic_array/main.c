#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char** allocate(FILE *file, int opened, int *allocated, char** database, int *logs); //definovanie funkcii na zaciatku
char** deallocate(char **database, int* logs, int* allocated);

void check_file(FILE *file) {  //kontrola otvorenia suboru
    if (file == NULL) {
        printf("Neotvoreny subor.\n");
        exit(0);
    }
}

void check_allocation(const char *line) {  //kontrola char dynamickeho pola
    if (line == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

void check_int_allocation(const int *line) {  //kontrola int dynamickeho pola
    if (line == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

void check_database_allocation(char **database) {  //kontrola alokacie char** dynamickeho pola
    if (database == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

int check_allocated(int allocated){  //kontrola alokacie
    if (!allocated){
        printf("Polia nie su vytvorene.\n");
        return 0;
    }
    return 1;
}

int check_opened(int opened){  //kontrola otvorenia suboru
    if (!opened){
        printf("Neotvoreny subor.\n");
        return 0;
    }
    return 1;
}

char* strip_name (char* line){ //vypis iba prveho mena
    line = strtok(line, "#");
    return line;
}

char* strip_line(char* line){ //funkcia na odstranenie konca riadku podla posledneho vyskytu znaku
    char *p = strchr(line, '\r'); //prepis \r na \0
    if (p != NULL)
        *p = '\0';
    else {
        p =  strchr(line, '\n');  //prepis \n na \0
        if (p != NULL)
            *p = '\0';
    }
    return line;
}

int print_line(char *line, int line_number){
    switch (line_number) {  //print podla hodnoty premennej, prestavenie na 0 po prekroceni 5
        case 1:
            printf("Nazov prispevku: %s", line);
            break;
        case 2:
            printf("Mena autorov: %s", line);
            break;
        case 3:
            printf("Typ prezentovania: %s", line);
            break;
        case 4:
            printf("Cas prezentovania: %s", line);
            break;
        case 5:
            printf("Datum: %s", line);
            break;
        default:
            printf("\n");
            line_number = 0;
    }
    line_number++;
    return line_number;
}

FILE* open_file(FILE *file, int* opened){  //v zo suboru
    if (!*opened){  // ak ešte pouzivatel nezadal "v"
        file = fopen("OrganizacnePodujatia.txt", "r");
        check_file(file);
        (*opened)++;
    }
    int size = 201;
    char *line = (char *) malloc(size * sizeof(char));
    check_allocation(line);
    int line_number = 1;
    printf("Vypis zo suboru:\n\n");
    while ((line = fgets (line, size, file)) != NULL) {  //citanie suboru
        line_number = print_line(line, line_number);
    }
    free(line);
    fseek(file, 0, SEEK_SET);
    return file;
}

char** print_allocated(char** database, int logs){  // v z poli
    printf("Vypis alokovanych prvkov:\n");
    for (int i = 0; i < logs*5; ++i) {
        switch (i%5){  //rozdelenie vypisu podla zvysku po deleni 5
            case 0:
                printf("Nazov prispevku: %s\n", database[i]);
                break;
            case 1:
                printf("Mena autorov: %s\n", database[i]);
                break;
            case 2:
                printf("Typ prezentovania: %s\n", database[i]);
                break;
            case 3:
                printf("Cas prezentovania: %s\n", database[i]);
                break;
            case 4:
                printf("Datum: %s\n\n", database[i]);
                break;
        }
    }
    return database;
}

int* swap(int* type, int index){  //vymena dvoch hodnot
    int temp = type[index];
    type[index] = type[index+1];
    type[index+1] = temp;
    return type;
}

int* sort(char** database, int* type, int index, int* change){ //bubblesort
    int ix1, ix2;
    for (int i = 0; i < index-1; ++i) {
        ix1 = type[i]; //index z databazy ulozeny v poli type
        ix2 = type[i+1];
        if(atoi(database[ix1])>atoi(database[ix2])){
            swap(type, i);  //vymena hodnot ak je cas vacsi
            (*change)++; //zvysenie premennej ak doslo k vymene
        }
    }
    if(*change){ //ak nastala zmena, nastavi premennu na 0 a vola rekurzivne sort
        (*change) = 0;
        sort(database, type, index, change);
    }
    return type;
}

void print_dates(char ** database, const int* type, int index){
    int ix;
    for (int i = 0; i < index; ++i) {
        ix = type[i];   //index databazy ulozeny v type
        printf("%s\t%s\t%s\t\t%s\n", database[ix], database[ix-1], strip_name(database[ix-2]), database[ix-3]);
    }
    printf("\n");
}

void date(char** database, int logs, int opened, int allocated, FILE* file){ //o
    int alloc = 1, u_index = 0, p_index = 0, change = 0;
    if (check_opened(opened) == 0){
        return;
    }
    if (!allocated){ //konťrola alokacie, ak nebola, vytvori nove polia
        alloc = 0;
        database = allocate(file, opened, &allocated, database, &logs);
    }
    char date[9];
    int *upud = (int*) malloc(logs* sizeof(int));
    check_int_allocation(upud);
    int *pppd = (int*) malloc(logs+1 * sizeof(int));
    check_int_allocation(pppd);
    printf("Zadaj datum:\n");
    scanf("%s", date);
    for (int i = 4; i < logs*5; i+=5) {  //prechadzanie datumami
        if((strcmp(database[i], date))==0){  //zhoda datumov
            if(strcmp(database[i-2], "UP") == 0 || strcmp(database[i-2], "UD") == 0){
                upud[u_index] = i-1;  //zapisat do pola upud index casu z databazy ak je typ UP alebo UD
                u_index++;
            }
            else{
                pppd[p_index] = i-1;  //inak zapisat do pola pppd
                p_index++;
            }
        }
    }
    upud = sort(database, upud, u_index, &change);  //zoradenie a vypis dvoch poli
    print_dates(database, upud, u_index);
    pppd = sort(database, pppd, p_index, &change);
    print_dates(database, pppd, p_index);
    if (!alloc) {
        deallocate(database, &logs, &allocated); //ak nebolo predtym alokovane, dealokuj
    }
}


int count_logs(FILE* file){ //spocitanie zapisov
    int lines = 0;
    int size = 200;
    char *line = (char*) malloc(size * sizeof(char));
    check_allocation(line);
    while((line = fgets(line, size, file)) != NULL){
        lines++;
    }
    fseek(file, 0, SEEK_SET);
    free(line);
    return (lines+1)/6; //pocet riadkov + 1 (posledny zapis nema prazdny riadok) / 6
}

char** allocate_objects(char ** database, char *line, int *line_number, int index){
    line = strip_line(line); //odstranenie \r pripadne \n z nacitania zo suboru
    switch (*line_number) { //alokovanie objektov, kopirovanie obsahu zo subora
        case 1:
            database[index] = (char*) malloc(150*sizeof(char)+1);
            check_allocation(*database);
            strcpy(database[index], line);
            break;
        case 2:
            database[index] = (char*) malloc(100*sizeof(char)+1);
            check_allocation(*database);
            strcpy(database[index], line);
            break;
        case 3:
            database[index] = (char*) malloc(2*sizeof(char)+1);
            check_allocation(*database);
            strcpy(database[index], line);
            break;
        case 4:
            database[index] = (char*) malloc(4*sizeof(char)+1);
            check_allocation(*database);
            strcpy(database[index], line);
            break;
        case 5:
            database[index] = (char*) malloc(8*sizeof(char)+1);
            check_allocation(*database);
            strcpy(database[index], line);
            break;
        default:
            (*line_number) = 0;
    }
    (*line_number)++;
    return database;
}

char** allocate(FILE *file, int opened, int *allocated, char** database, int *logs){  //n
    if (check_opened(opened) == 0){
        return database;
    }
    if (*allocated){
        deallocate(database, logs, allocated);
        printf("Vycistena dynamicka pamat.\n");
    }
    int line_number = 1, index = 0;
    int size = 200;
    char *line = (char*) malloc(size * sizeof(char));
    check_allocation(line);
    *logs = count_logs(file);  //zistenie poctu zapisov
    database = (char**) malloc(((*logs)*5)*sizeof(char*));
    check_database_allocation(database);
    while((line = fgets(line, size, file)) != NULL) {
        database = allocate_objects(database, line, &line_number, index); //alokovanie objektu a naplnenie
        if (line_number != 1) {
            index++;
        }
    }
    fseek(file, 0, SEEK_SET);
    free(line);
    *allocated = 1;
    printf("Nacitane data.\n");
    return database;
}

void date_type(char** database, int logs, int allocated){ //s
    if (check_allocated(allocated) == 0){
        return;
    }
    int found_dates = 0;
    char date[9];
    char type[3];
    printf("Zadaj datum a typ prezentovania:\n");
    scanf("%s %s", date, type);
    for (int i = 2; i < logs*5; i+=5) {
        if (((strcmp(database[i], type)) == 0) && (strcmp(database[i+2], date)) == 0){  //zhoda v type a datume
            printf("%s\t%s\t\t%s\n", database[i + 1], strip_name(database[i - 1]), database[i - 2]);
            found_dates = 1;
        }
    }
    if (!found_dates){ //ak sa nenasla zhoda
        printf("Pre dany vstup neexistuju zaznamy.\n");
    }
}

int create_time_index(int time){  //vytvorenie indexu pre casovy rozmer v histograme
    switch (time){
        case 800 ... 959:
            return 0;
        case 1000 ... 1150:
            return 1;
        case 1200 ... 1359:
            return 2;
        case 1400 ... 1559:
            return 3;
        case 1600 ... 1759:
            return 4;
        case 1800 ... 1959:
            return 5;
    }
}

int create_type_index(char type[]){  //vytvorenie indexu pre typovy rozmer v histograme
    if (strcmp(type, "UP") == 0)
    {
        return 0;
    }
    else if (strcmp(type, "UD") == 0)
    {
        return 1;
    }
    else if (strcmp(type, "DP") == 0)
    {
        return 2;
    }
    else if (strcmp(type, "DD") == 0)
    {
        return 3;
    }
}


void process_histogram(char** database, int logs, int histogram[6][4]){
    int time, time_index, type_index;
    char type[3];
    for (int i = 3; i < logs*5; i+=5) {
        time = atoi(database[i]);  //do premennej ulozi ciselnu hodnotu casu
        strcpy(type, database[i-1]);  //kopirovanie typu
        time_index = create_time_index(time);  //zistenie indexov
        type_index = create_type_index(type);
        histogram[time_index][type_index]++;  //inkrementovanie spravnej hodnoty histogramu
    }
}

void print_histogram(int histogram[6][4]){  //vypis histogramu
    printf("hodina\t\t\tUP\tUD\tPP\tPD\n");
    for (int i = 0; i < 6; ++i) {
        switch (i){
            case 0:
                printf("08:00 - 09:59 :\t\t");
                break;
            case 1:
                printf("10:00 - 11:59 :\t\t");
                break;
            case 2:
                printf("12:00 - 13:59 :\t\t");
                break;
            case 3:
                printf("14:00 - 15:59 :\t\t");
                break;
            case 4:
                printf("16:00 - 17:59 :\t\t");
                break;
            case 5:
                printf("18:00 - 19:59 :\t\t");
                break;
        }
        printf("%d\t%d\t%d\t%d\n", histogram[i][0], histogram[i][1], histogram[i][2], histogram[i][3]);
    }
}

char** histogram(char** database, int logs, int allocated){  //h
    if (check_allocated(allocated) == 0){
        return database;
    }
    int histogram[6][4] = {0}; //2D pole na histogram, prvy index je casovy interval, druhy je typ- UP, UD PP, PD
    process_histogram(database, logs, histogram); //spracovanie histogramu - uprava hodnot z 0 na spravne pocty
    print_histogram(histogram);  //vypis histogramu
    return database;
}

int* find_index(int* remove_indices, char** database, char subject[], const int* logs){  //zistenie indexov odstranenia
    int counter = 0;
    for (int i = 0; i < (*logs)*5; i+=5) {
        if((strcmp(strip_line(subject), database[i])) == 0){  //porovnanie vstupu s nazvami v databaze
            remove_indices[counter] = 1;  //ak sa zhoduju, nastavi index reprezentujuci cislo zapisu na 1
        }
        counter++;
    }
    return remove_indices;
}

char** move_database(char** database, int* logs, const int* remove_indices){
    int change = 0;  //premenna zmeny, dolezita v pripade ze su 2 zaznamy s rovnakym menom, upravuje prepis for cyklov
    for (int i = 0; i < *logs; ++i) {
        if (remove_indices[i] == 1) {  //pokial je hodnota v poli mazania indexov 1
            for (int j = (i*5 + 5)-change*5; j < ((*logs) * 5)-change*5; ++j) {  //zmensovanie cyklu v pripade zmeny
                strcpy(database[j - 5], database[j]);  //prekopiruje data od premazaneho miesta o 5 miest spat
            }
            for (int j = (*logs * 5 - 5)-change*5; j < (*logs*5)-change*5; j++) {  //zmensovanie cyklu v pripade zmeny
                free(database[j]);  //uvolnenie poslednych 5 prvkov v databaze, nakolko predosle sa prepisali
            }
            change++;
        }
    }
    (*logs) -= change;  //uprava poctu zapisov o pocet zmien
    database = realloc(database, *logs*5*sizeof(char*));  //realokovanie databazy
    check_database_allocation(database);
    printf("Vymazalo sa : %d zaznamov.\n", change);
    return database;
}

char** remove_data(char** database, int* logs, int allocated){ //z
    if (check_allocated(allocated) == 0){
        return database;
    }
    getchar();
    int *remove_indices; //zoznam zaznamov
    remove_indices = malloc(*logs*sizeof(int));
    check_int_allocation(remove_indices);
    for (int i=0; i<*logs; i++)  //inicializacia na 0
    {
        remove_indices[i] = 0;
    }
    char subject[151];
    printf("Zadaj nazov prispevku:\n");
    fgets(subject, 151, stdin); //nacitanie stringu aj s medzerami
    remove_indices = find_index(remove_indices, database, subject, logs);  //uprava hodnot na 1 pre zapisy na mazanie
    database = move_database(database, logs, remove_indices);  //posun hodnot v poli a uvolnenie a realokacia
    free(remove_indices);
    return database;
}

char ** allocate_new(char** database, int* logs){  //p
    char subject[151], name[101], type[3], time[5], date[9];
    int line_number = 1;
    printf("Zadaj nove udaje do databazy:\n");
    getchar();
    fgets(subject, 151, stdin);
    fgets(name, 101, stdin);
    scanf("%s", type);
    scanf("%s", time);
    scanf("%s", date);
    (*logs)++;
    database = realloc(database, ((*logs)*5)*sizeof(char*));  //realokovanie o jednu polozku navyse
    check_database_allocation(database);
    database = allocate_objects(database, subject, &line_number, (*logs)*5-5);  //alokacia objektov
    database = allocate_objects(database, name, &line_number, (*logs)*5-4);
    database = allocate_objects(database, type, &line_number, (*logs)*5-3);
    database = allocate_objects(database, time, &line_number, (*logs)*5-2);
    database = allocate_objects(database, date, &line_number, (*logs)*5-1);
    printf("Zaznam sa podarilo pridat.\n");
    return database;
}

char** deallocate(char **database, int* logs, int* allocated){ //k
    for (int i = 0; i < (*logs)*5; ++i) {
        free(database[i]);  //uvolnenie stringov
    }
    *logs = 0;  //uprava premennych
    *allocated = 0;
    free(database);  //uvolnenie pola databaza
    return database;
}

void change_histogram(int digits[], char ** database, int index){
    for (int i = 0; i < 4; i++) {
        char date_str = database[index][i];
        int digit = atoi(&date_str);
        digits[digit]++;
    }
}

void print_i_histogram(const int digits[]){
    for (int i = 0; i < 10; ++i) {
        if(digits[i] != 0){
            printf("%d: ", i);
            for (int j = 0; j < digits[i]; j++) {
                printf("*");
            }
            printf("\n");
        }
    }
}

void task_1(char** database, int logs, int allocated){ // i
    if(check_allocated(allocated) == 0){
        return;
    }
    int k;
    int digits [10] = {0};
    printf("Zadaj cele cislo k:\n");
    scanf("%d", &k);
    if(k<1){
        printf("Zla hodnota k\n");
        return;
    }
    for (int i = 3; i < logs*5; i+=5) {
        int date_number = atoi(database[i]);
        if (date_number % k == 0){
            change_histogram(digits, database, i);
        }
    }
    print_i_histogram(digits);
}

char** deallocate_authors(char **database, int* amount){ //k
    for (int i = 0; i < *amount; ++i) {
        free(database[i]);
    }
    *amount = 0;
    free(database);
    return database;
}

char ** task_2(char** database, int logs, int* amount, char** new_authors, int allocated){ //y
    if (!check_allocated(allocated)){
        return new_authors;
    }
    getchar();
    new_authors = (char**) malloc(logs*sizeof(char*));
    check_database_allocation(new_authors);
    char* part = (char*) malloc(logs*sizeof(char));
    check_allocation(part);
    printf("Zadaj cast stringu ktore sa bude hladat v menach:\n");
    fgets(part, 151, stdin);
    part = strip_line(part);
    for (int i = 0; i < strlen(part); ++i) { //duplicita, von z funkcie
        part[i] = tolower(part[i]);
    }
    for (int i = 1; i < logs*5; i+=5) {
        char* temp = (char*) malloc(sizeof(char)*(strlen(database[i])));
        check_allocation(temp);
        strcpy(temp, database[i]);
        for (int j = 0; j < strlen(temp); j++) {
            temp[j] = tolower(temp[j]);
        }
        if (strstr(temp, part)!=NULL){
            new_authors[*amount] = (char*) malloc (sizeof(char)*strlen(temp));
            check_allocation(temp);
            strcpy(new_authors[*amount], database[i]);
            (*amount)++;
        }
        free(temp);
    }
    new_authors = realloc(new_authors, (*amount)*sizeof(char*));
    if(!new_authors){
        deallocate_authors(new_authors, amount);
    }
    free(part);
    return new_authors;
}

int main() {  //spravit kontrolu na n a v vo vlastnej funkcii?
    int input, opened = 0, allocated = 0, amount_of_strings = 0; // konrola otvorenia a alokovania
    int logs = 0;  //pocet zapisov
    char** database;// dynamicka pamat ktora obsahuje ukazovatele na polozky zo suboru
    char** authors;
    FILE *file;
    while (1) {
        input = getchar();
        switch (input) {
            case 'v':
                if(!allocated) { //este nebola vykonana alokacia
                    file = open_file(file, &opened);
                }
                else {
                    database = print_allocated(database, logs);
                }
                break;
            case 'o':
                date(database, logs, opened, allocated, file);
                break;
            case 'n':
                database = allocate(file, opened, &allocated, database, &logs);
                break;
            case 's':
                date_type(database, logs, allocated);
                break;
            case 'h':
                database = histogram(database, logs,  allocated);
                break;
            case 'z': //
                database = remove_data(database, &logs, allocated);
                break;
            case 'p':
                database = allocate_new(database, &logs);
                break;
            case 'k':
                deallocate(database, &logs, &allocated); //&opened);
                fclose(file);
                return 0;
            case 'i':
                task_1(database, logs, allocated);
                break;
            case 'y':
                if(amount_of_strings){
                    deallocate_authors(authors, &amount_of_strings);
                }
                authors = task_2(database, logs, &amount_of_strings, authors, allocated);
                printf("Vypis mien z vytvoreneho zoznamu:\n");
                for (int i = 0; i < amount_of_strings; i++) {
                    printf("%s\n", authors[i]);
                }
                break;
            default:
                continue;
        }
    }
}

