# X.necrophora.secondaryMetabolites.

## Below is information regarding methods pertinent to the data in this repository. These methods are an excerpt from the manuscript submitted to Plant Health Progress (March 2022).

**Title:**
# Secondary metabolites produced by <i>Xylaria necrophora</i> are responsible for symptoms associated with taproot decline of soybean.

Authors: Teddy Garcia-Aroca<sup>a,d,+</sup>, Trey Price<sup>b</sup>, Jose Solorzano<sup>c</sup>, David Galo<sup>a</sup>, Sophie B. Sheffield<sup>a</sup>, and Vinson P. Doyle<sup>a,+</sup>

<sup>+</sup> Correspondence to: Teddy Garcia-Aroca (tgarciaaroca@albany.edu / tedggarcia@gmail.com) or Vinson P. Doyle (VDoyle@agcenter.lsu.edu);

<sup>a</sup> Department of Plant Pathology & Crop Physiology, Louisiana State University, Baton Rouge, LA; 

<sup>b</sup> LSU AgCenter, Macon Ridge Research Station, Winnsboro, LA;

<sup>c</sup> Department of Plant Pathology, University of Minnesota, Saint Paul, MN;

<sup>d</sup> Department of Biological Sciences, University at Albany, State University of New York, Albany, NY;


Teddy Garcia-Aroca: http://orcid.org/0000-0002-7567-4363
Paul “Trey” Price: http://orcid.org/0000-0002-1004-3616
Vinson P. Doyle: http://orcid.org/0000-0002-2350-782X


## Digital measurements of chlorophyll content

Chlorophyll content was measured digitally from photos taken on the last day of our experiments, with the protocol provided by Liang et al. (2017)[^1]. Photos were taken on the last day of the experiment at either 7 or 14 days of exposure (DOE) with a Nikon D5300 (Nikon USA, Melville, New York) camera fixed to a photo stand surrounded by two Neewer NL660 LED lamps (Shenzhen Neewer, Shenzhen, China), and stored in RAW format (“.NEF” extension), under these parameters: aperture=f/11, ISO (sensitivity of the signal gain of the camera's sensor) = 640. A standard ruler and an X-rite color checker chart (X-rite, Grand Rapids, MI) were included in each photo for white background calibration purposes. The white balance was standardized on Adobe Lightroom Classic (ALC) (Adobe, San Jose, California) as suggested by Liang et al. (2017)[^1] prior to subsampling in ImageJ 1.8.0 (Schneider et al., 2012)[^2]. The photos in RAW format were loaded on ALC, and the white background was standardized using a pre-made camera profile obtained with DNG profile editor (Adobe, San Jose, California) and the X-rite color checker chart, as described by Liang et al. (2017)[^1]. Photos with standardized white background were saved as JPEG format for further processing.

Three leaf discs of 6 mm in diameter were subsampled from each photo by calibrating the number of pixels per millimeter using a ruler present in all raw photos. Subsamples were taken along the main vein of the center leaflet, at the main trifoliate, or the trifoliate from the highest node. Subsamples were taken at the base (sample 1), middle (sample 2), and edge (sample 3) of the leaflet and saved for further processing. These 6-mm leaf discs were extracted digitally from leaves by copying and pasting a "Region of Interest" (ROI) of 6-mm (calibrated with the ruler) into a new RGB, 512 × 512-pixel image with a black background (only one slice) and saved as separate files for further processing. The black background of sampled leaf discs was removed using Adobe Photoshop CS6 (Adobe, San Jose, California) and saved as a new clean image. Total chlorophyll content was measured from clean images in nanograms per square millimeter (ng/sq mm) using ImageJ 1.8.0 software (Schneider et al., 2012)[^2] and the JAVA scripts provided by Liang et al. (2017)[^1].


## Measurements of root growth

Root growth from the base of the soybean (cv. AG4632) stem cuttings was observed in our experiments at 14 DOE and used as a response variable. Roots were measured from photos taken the last day of each experiment using ImageJ 1.8.0 software (Schneider et al., 2012)[^2].


## Statistical tests

Total chlorophyll content and root length estimated digitally were used as the main response variables among treatments. Comparisons were made using the DYPLR 0.8.4 (Wickham et al., 2018)[^3] and AGRICOLAE packages (de Mendiburu 2020)[^4] in R Studio 1.2.5 (RStudio Team, 2015)[^5]. The main effects from treatments (isolates, cultivars, plant species, and their respective controls), fermentation conditions, dilution factors, and their respective technical replicates (flasks, stem cuttings, and leaf samples taken from each stem cutting), were estimated in comparisons for complete randomized designs (CRD) with an ANOVA test, under the general linear model assumptions. The null hypothesis of no statistical difference was rejected when P≤ 0.05 among main effects. To determine groups that were statistically different within main effects that were found to be significantly different in ANOVA, a subsequent post-hoc Tukey’s honestly significant difference (HSD) test was run for pairwise and multiple comparisons (Tukey, 1977)[^6] with an alpha of 0.05. The Pearson’s correlation coefficient was used to compare chlorophyll content extracted digitally and chemically for each experimental unit for validation purposes and to test the potential correlation of the final pH measurements with digital chlorophyll extractions, under the assumptions of the general linear model. All experiments consisted of at least two flask replications (flasks representing technical repetitions for isolates and control) per fermentation condition and dilution and three technical replications (three different stem cuttings treated, representing technical repetitions for stem cuttings). All experiments were repeated at least once.


## Description of the folders provided in this repository

### analyses

In this folder we provide the R markdown with most statistical analyses using the data provided in raw_data. We have provided the results of statistical analyses with both raw and normalized data for comparison.

### output

Here we provide output from R markdown, plots, and composite figures found in the manuscript.

### raw_data

In this folder, we have provided all the datasets used in our analyses.

### scripts

Here we provide an example of the JAVA scripts and macros used on ImageJ to measure chlorophyll content recursively on leaf discs extracted from photos taken on the last day of our experiments.
1. input = this folder contains leaf discs extracted and processed as detailed above.
2. macros = contains one example macro used to measure chlorophyll content recursively for leaf disc samples (JPEG files) provided in the input folder.
3. output = contains all the processed leaf discs present in input folder. Red values represent the total chlorophyll content measured in nanograms per square millimeters.
4. plugins = contains all the plugins described in Liang et al. (2017)[^1]. The plugin used in the current example was modified to adjust white-background and renamed as "Chlorophyll_Imager_Teddy_ES5.java". This pluging has to be loaded on ImageJ and the associated ".class" file has to be replaced for the changes to take effect.


## Instructions

When downloading this repository, open the "analyses" folder and load the R markdown to your Rstudio (or directly into R) and run the code to inspect the results presented in this manuscript. Alternatively, you can read the results provided in the html or PDF versions in the "output"/"analyses" folders.


## Notes

1. Not all datasets have been used for plotting and illustration purposes, but all datasets are provided to allow reproducibility of our analyses and plotting of extra figures.
2. Not all the R-packages used for these analyses have been cited, but we acknowledge the work of other scientists and we do not claim ownership of any of these packages. 

## References

[^1] Liang Y, Urano D, Liao KL, Hedrick TL, Gao Y, Jones AM. 2017. A nondestructive method to estimate the chlorophyll content of Arabidopsis seedlings. Plant Methods. 13:1–10. https://doi.org/10.1186/s13007-017-0174-6

[^2] Schneider CA, Rasband WS, Eliceiri KW. 2012. NIH Image to ImageJ: 25 years of image analysis. Nature Methods. 9:671–675. https://doi.org/10.1007/978-1-84882-087-6_9

[^3] Wickham H, François R, Henry L, Müller K. 2018. dplyr: A grammar of data manipulation. https://cran.r-project.org/package=dplyr

[^4] de Mendiburu, F. 2020. agricolae: statistical procedures for agricultural research. https://cran.r-project.org/package=agricolae

[^5] RStudio Team. 2015. RStudio: Integrated development environment for R. RStudio, Inc, Boston, MA. http://www.rstudio.com.

[^6] Tukey JW. 1977. Exploratory Data Analysis. Biometrics. 33:768. http://www.jstor.org/stable/2529486








