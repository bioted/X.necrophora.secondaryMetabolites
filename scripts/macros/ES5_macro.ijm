function action(input, output, filename) {
open(input + filename);
run("Chlorophyll Imager", "number=1 number_0=1 r_=224.519 g=221.422 b=214.459 a1=-0.0280 a2=0.0190 a3=-0.0030 a4=5.780");
saveAs("Jpeg", output + filename);
close();
}

input = "/Users/tedggarcia/Documents/X.necrophora.secondaryMetabolites/scripts/input/";
output = "/Users/tedggarcia/Documents/X.necrophora.secondaryMetabolites/scripts/output/";

setBatchMode(true); 
list = getFileList(input);
for (i = 0; i < list.length; i++)
        action(input, output, list[i]);
setBatchMode(false);close();
