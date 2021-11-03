# X.necrophora.secondaryMetabolites

## Below is information regarding methods pertinent to the data in this repository. These methods are an excerpt from the manuscript submitted to Plant Disease.

Title: Secondary metabolites produced by <i>Xylaria necrophora</i> are responsible for symptoms associated with taproot decline of soybean.

Authors: Teddy Garcia-Aroca<sup>a,+</sup>, Trey Price<sup>b</sup>, Jose Solorzano<sup>c</sup>, David Galo<sup>a</sup>, Sophie B. Sheffield<sup>a</sup>, and Vinson P. Doyle<sup>a,+</sup>

<sup>+</sup> Correspondence to: Teddy Garcia-Aroca (TGarciaAroca@agcenter.lsu.edu, tedggarcia@gmail.com) or Vinson P. Doyle (VDoyle@agcenter.lsu.edu);

<sup>a</sup> Department of Plant Pathology & Crop Physiology, Louisiana State University, Baton Rouge, LA; 

<sup>b</sup> LSU AgCenter, Macon Ridge Research Station, Winnsboro, LA;

<sup>c</sup> Department of Plant Pathology, University of Minnesota, Saint Paul, MN;



## Methods (Related to measurements of cholophyll content)

Final photos were taken at either 7 or 14 days of exposure (DOE) with a Nikon D5300 (Nikon USA, Melville, New York) camera fixed to a photo stand surrounded by two Neewer NL660 LED lamps (Shenzhen Neewer, Shenzhen, China), and stored in RAW format (“.NEF” extension), under these parameters: aperture=f/11, ISO (sensitivity of the signal gain of the camera's sensor) = 640. A standard ruler and an X-rite color checker chart (X-rite, Grand Rapids, MI) were included in each photo for white background calibration purposes. The white balance was standardized on Adobe Lightroom Classic (ALC) (Adobe, San Jose, California) as suggested by Liang et al. (2017) prior subsampling in ImageJ 1.8.0 software (Schneider et al. 2012). The photos in RAW format were loaded on ALC, and the white background was standardized using a pre-made camera profile obtained with DNG profile editor (Adobe, San Jose, California) and the X-rite color checker chart, as described by Liang et al. (2017). Photos with standardized white background were saved as JPEG format for further processing.

Three leaf discs of 6 mm in diameter were subsampled from each photo by calibrating the number of pixels per millimeter using a ruler present in all raw photos. Subsamples were taken along the main vein of the center leaflet, at the main trifoliate, or the trifoliate from the highest node. Subsamples were taken at the base (sample 1), middle (sample 2), and edge (sample 3) of the leaf and saved for further processing. These 6-mm leaf discs were extracted digitally from leaves by copying and pasting a "Region Of Interest" (ROI) of 6-mm (calibrated with the ruler) into a new RGB, 512 × 512-pixel image with a black background (only one slice) and saved to a new folder for further processing. The black background of sampled leaf discs was removed using Adobe Photoshop CS6 (Adobe, San Jose, California) and saved as a new “clean” image. Total chlorophyll content was measured from “clean” images in nanograms per square millimeter (ng/sq mm) using ImageJ 1.8.0 software (Schneider et al. 2012) and the JAVA scripts provided by Liang et al. (2017).


## Statistical tests

Total chlorophyll content and root length were used as response variables among treatments. Comparisons were made using the DYPLR 0.8.4 (Wickham et al. 2018) and AGRICOLAE packages (Mendiburu 2020) in R Studio 1.2.5 (RStudio Team 2015). The main effects from treatments (isolates, cultivars, and host species, and their respective controls), isolate growth conditions, and dilution factors, as well as the effect of CFCFs across soybean cultivars and plant species at a constant concentration, were estimated in comparisons for complete randomized designs (CRD) with an ANOVA test (P ≤ 0.05), under the general linear model assumptions, and a post-hoc Tukey’s honestly significant difference (HSD) test for pairwise and multiple comparisons (Tukey 1977). The Pearson’s correlation coefficient was used to compare chlorophyll content extracted digitally and chemically for each experimental unit for validation purposes and to test the potential correlation of the final pH measurements with digital chlorophyll extractions, under the assumptions of the general linear model.


## Description of the folders provided in this repository

### raw_data

In this folder, we have provided all the datasets used in our analyses.

### analyses

In this folder we provide the R markdown with all statistical analyses using the data provided.

### output

Here we provide output from R markdown, plots, and composite figures found in the manuscript.

### scripts

Here we provide an example of the JAVA scripts and macros used on ImageJ to measure chlorophyll content recursively on leaf discs extracted from photos taken on the last day of our experiments.
1. input = folder containing leaf discs extracted and processed as detailed above.
2. macros = contains one example macro used to measure chlorophyll content recursively for all photos in the input folder.
3. output = contains all the processed leaf discs present in input folder. Red values represent the total chlorophyll content measured in nanograms per square millimeters.
4. plugins = contains all the plugins described in Liang et al. (2017). The plugin used in the current example was modified to adjust white-background and renamed as "Chlorophyll_Imager_Teddy_ES5.java". This pluging has to be loaded on ImageJ and the associated ".class" file has to be replaced for the changes to take effect.


## Instructions

When downloading this repository, open the "analyses" folder and load the R markdown to your Rstudio (or directly into R) and run the code to inspect the results presented in this manuscript. Alternatively, you can read the results provided in the html and PDF versions in the "output" folder.

## References


Liang Y, Urano D, Liao KL, Hedrick TL, Gao Y, Jones AM. 2017. A nondestructive method to estimate the chlorophyll content of Arabidopsis seedlings. Plant Methods. 13:1–10

Mendiburu F de. 2020. agricolae: statistical procedures for agricultural research.

RStudio Team. 2015. RStudio: Integrated development environment for R. RStudio, Inc, Boston, MA. http://www.rstudio.com.

Schneider CA, Rasband WS, Eliceiri KW. 2012. NIH Image to ImageJ: 25 years of image analysis. Nature Methods. 9:671–675.

Tukey JW. 1977. Exploratory Data Analysis. Biometrics. 33:768.

Wickham H, François R, Henry L, Müller K. 2018. dplyr: A grammar of data manipulation.







