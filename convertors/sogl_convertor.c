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

        char buf[128];
        for(int i=0; i<128; i++){
            buf[i]=0;
        }

        int n_spr;
        fscanf(f, "%i\n", &n_spr);

        //write len
        writeLInt(n_spr, out);
        for(int j=0;j<n_spr;j++){
            //clean
            for(int i=0;i<128;i++){
                buf[i]=0;
            }
            //read string
            for(int i=0;i<128;i++){
                char chr = fgetc(f);
                if(chr == ' ')break;
                buf[i] = chr;
            }

            //write sprite name
            writeInt(strlen(buf), out);
            fprintf(out, "%s", buf);

            int n_frame;
            fscanf(f, "%i", &n_frame);
            //write number of frames
            writeInt(n_frame, out);
            for(int i=0;i<n_frame;i++){
                int x,y,w,h,cx,cy;
                fscanf(f, "%i %i %i %i %i %i", &x, &y, &w, &h, &cx, &cy);
                writeLInt(x, out);
                writeLInt(y, out);
                writeInt(w, out);
                writeInt(h, out);
                writeInt(cx, out);
                writeInt(cy, out);
            }
            while(fgetc(f)!='\n');
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
