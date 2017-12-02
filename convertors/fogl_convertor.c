#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void writeLInt(int x, FILE* f);
void writeInt(int x, FILE* f);
int main(int argn, char** argv){
    if(argn == 2){
        FILE* f = fopen(argv[1], "r");
        int n = strlen(argv[1]);
        argv[1][n-3] = 0;
        FILE* out = fopen(argv[1], "wb");

        int x, y, w, h, d, b, a;
        char c;
        fscanf(f, "%i %i\n", &x, &y);

        writeLInt(x, out);
        writeLInt(y, out);

        while(!feof(f)){
            fscanf(f, "%c %i %i %i %i %i %i %i\n", &c, &x, &y, &w, &h, &d, &b, &a);
            writeInt(c, out);
            writeLInt(x, out);
            writeLInt(y, out);
            writeInt(w, out);
            writeInt(h, out);
            writeInt(d, out);
            writeInt(b, out);
            writeInt(a, out);
        }

        fclose(f);
        fclose(out);
    }
    return 0;
}

void writeLInt(int x, FILE* f){
    writeInt(x, f);
    writeInt(x>>8, f);
}
void writeInt(int x, FILE* f){
    fputc(x & 0xff, f);
}
