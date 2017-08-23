# moFF-gui #

* [Introduction](#introduction)
* [Read Me](#read-me)




|   |   |   |
| :-------------------------: | :---------------: | :--: |
| [![download](https://github.com/compomics/moff-gui/wiki/images/button_3.png)](https://github.com/compomics/moff-gui/releases/download/v1.1.0/moff-gui-1.1.0.zip) | *v1.1.0 - All platforms* | [ReleaseNotes](https://github.com/compomics/moff-gui/releases/tag/0.1.0-beta) |


|   |   |
| :----------------------------------: | :-----------------------------------: | 
| [![](https://github.com/compomics/moff-gui/wiki/images/1_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/1_snap.PNG) |  [![](https://github.com/compomics/moff-gui/wiki/images/2_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/2_snap.PNG) |
| [![](https://github.com/compomics/moff-gui/wiki/images/3_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/3_snap.PNG) |  [![](https://github.com/compomics/moff-gui/wiki/images/4_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/4_snap.PNG) |

(Click on a figure to see the full size version)





# Introduction #
moFF-gui is an intuitive graphical user interface for the moFF (modest Feature Finder) algorithm. MoFF is a fast and lightweight tool for the quantification of peptides directly from Thermo RAW files. Being programmed in the JAVA programming language, moFF-gui is able to run platform independently and was extensively tested on windows and linux. The tool can be readily be integrated in pipelines using PeptideShaker as it accepts cps files as input. Alternatively, a tab separated file (tsv) can be provided with minimal [annotation requirements](##Input Data) on the level of peptide identifications. A [command-line version](https://github.com/compomics/moFF) of moFF is also available.

[Go to top of page](#moff-gui)



# Read me #
  * [Minimum Requirements](#minimum-requirements)
  * [Input Data](#input-data)
  * [Sample Data](#sample-data)
  * [moFF Parameters and Options](#moff-parameters-and-options)
  * [moFF Output](#moff-output)


## Minimum Requirements ##

Required java version :
- Java Runtime Environment (JRE) 8

Required python libraries :
- Python 2.7
- pandas  > 0.17.
- numpy > 1.10.0
- argparse > 1.2.1 
- scikit-learn > 0.17

Optional requirements :
-When using PeptideShaker results as a source, a PeptideShaker installation (<http://compomics.github.io/projects/peptide-shaker.html>) needs to be availabe.
 

During processing, moFF makes use of a third party algorithm (txic or txic.exe) which allows for the parsing of the Thermo RAW data. 
Txic is compatible with the raw outputfiles originating from any Orbitrap or triple quadrupole Thermo machine. However, Thermo Fusion instruments are currently not supported.

[Go to top of page](#moff-gui)

--- 
##Input Data

moFF-GUI requires two types of input for the quantification procedure :
 - Thermo RAW file 
 - MS2 identified peptide information

The MS2 identified peptides can be provided in two different ways:
- As output of PeptideShaker (.cpsx) (a)
- A tab-delimited file with mimimal annotation for each peptide (b)

(a) The tab-delimited file must contain the following information for all the peptides:
  - 'peptide' : sequence of the peptide
  - 'prot': protein ID 
  - 'rt': peptide retention time  ( The retention time must be specified in second )
  - 'mz' : mass over charge
  - 'mass' : mass of the peptide
  - 'charge' : charge of the ionized peptide
 
NOTE : In case the tab-delimited file provided by the user contains also other fields (i.e modifications,petides length), those will remain in the result output.

(b) When using a cpsx file, the respective spectra (MGF) and sequence database (.FASTA) that were used in the PeptideShaker processing must be provided. A detailed tutorial on PeptideShaker can be found at <http://compomics.com/bioinformatics-for-proteomics/>

[Go to top of page](#moff-gui)

---
###Sample Data

Sample data is provided at [folder] (genesis.ugent.be/uvpublicdata/moff_sample_data.zip) . It contains the cpsx and the raw files related to 3 runs of CPTAC Study 6 (Paulovich, MCP Proteomics, 2010). 

The cpsx files were generated through a pipeline consisting of SearchGUI and PeptideShaker. The used search algorithms were X!Tandem and MSGF+. The sequence database (FASTA) and spectra (MGF) for all 3 runs are also provided.


[Go to top of page](#moff-gui)

---
## moFF Parameters and Options 

moFF  can be run in two modes:
- Apex mode : extract only the apex peak for the MS2 input peptides
- Match-between-run (MBR) mode : match unidenfied peptides across all runs in order to increase the number of quantified peptides.

In the apex mode , the parameters are :
 - precursor match tollerance of the machine in ppm
 - xic retention time window (minute). Default value is 5 min.
 - peak retention time window (minute). Default value is 0.2 min.

In the match-between-run some more parameters are requested:
 - 	matched peak retention time window (minute). Default value is 0.4 
 -  combination weighting (on/off)
 - 	filter outlier and width of the filter (higher value mean less filtering effect)

The match-between-run uses all the shared peptides among the replicates to train the RT prediction models as a default option. In case the user wants to provide a specific set of peptides as training set, it is possible to specify them in a tab delimited format with two fields : peptides and mass.


[Go to top of page](#moff-gui)

---
## moFF Output

The output consists of : 

- Tab delimited file (with the same name of the input raw file) that contains the apex intensity values and some other information (a)
- Log file specific to the apex module (b) or the MBR module (c)

(a) Description of the fields added by moFF in the output file:

Parameter | Meaning
--- | -------------- | 
*rt_peak* | retention time (in seconds) of the discovered apex peak
*SNR*     | signal-to-noise  ratio of the peak intensity.
*log_L_R*'| peak shape. 0 indicates that the peak is centered. Positive or negative values are an indicator for respectively right or left skewness 
*intensity* |  MS1 intensity
*log_int* | log 2 transformed MS1 intensity 
*lwhm* | first rt value where the intensity is at least the 50% of the apex peak intensity on the left side
*rwhm* | first rt value where the intensity is at least the 50% of the apex peak intensity on the right side
*5p_noise* | 5th percentile of the intensity values contained in the XiC. This value is used for the *SNR* computation
*10p_noise* |  10th percentile of the intensity values contained in the XiC.
*code_unique* | this field is concatenation of the peptide sequence and mass values. It is used by moFF during the match-between-runs.
*matched* | this value indicated if the featured has been added by the match-between-run (1) or is a ms2 identified features (0) 

(b) A log file is also provided containing the process output. 

(c) A log file where all the information about all the trained linear model are displayed.

NOTE : The log files and the output files are in the output folder specified by the user. 

[Go to top of page](#moff-gui)
