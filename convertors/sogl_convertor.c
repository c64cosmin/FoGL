#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define NBUF 256

void writeLInt(int x, FILE* f);
void writeInt(int x, FILE* f);
int main(int argn, char** argv){
    if(argn == 2){
        printf("Converting %s\n", argv[1]);
        FILE* f = fopen(argv[1], "r");
        char* extension = ".sheet";
        int n = strlen(argv[1]);
        char* filePath = argv[1];
        char newFilePath[NBUF*2] = {};
        strcpy(newFilePath, filePath);
        strcat(newFilePath, ".bin");
        FILE* out = fopen(newFilePath, "wb");
        if(!out){
            printf("Failed to convert!\n");
            return 1;
        }
        printf("Opened %s\nWill convert to %s\n", filePath, newFilePath);

        char buf[NBUF];
        for(int i=0; i<NBUF; i++){
            buf[i]=0;
        }

        int n_img;
        fscanf(f, "%i\n", &n_img);
        printf("Found %i images\n", n_img);
        while(n_img--)fgets(buf, NBUF, f);

        int n_spr;
        fscanf(f, "%i\n", &n_spr);

        //write len
        writeLInt(n_spr, out);
        for(int j=0;j<n_spr;j++){
            //clean
            for(int i=0;i<NBUF;i++){
                buf[i]=0;
            }
            //read string
            for(int i=0;i<NBUF;i++){
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
            while(fgetc(f)!='\n' && !feof(f));
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
