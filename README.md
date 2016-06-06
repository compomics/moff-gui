# moFF-gui #

* [Introduction](#introduction)
* [Read Me](#read-me)




|   |   |   |
| :-------------------------: | :---------------: | :--: |
| [![download](https://github.com/compomics/moff-gui/wiki/images/button_3.png)](https://github.com/compomics/moff-gui/releases/download/0.1.0-beta/moff-gui-0.1.0-beta.zip) | *v0.1.0-beta - All platforms* | [ReleaseNotes](https://github.com/compomics/moff-gui/releases/tag/0.1.0-beta) |


|   |   |
| :----------------------------------: | :-----------------------------------: | 
| [![](https://github.com/compomics/moff-gui/wiki/images/1_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/1_snap.PNG) |  [![](https://github.com/compomics/moff-gui/wiki/images/2_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/2_snap.PNG) |
| [![](https://github.com/compomics/moff-gui/wiki/images/3_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/3_snap.PNG) |  [![](https://github.com/compomics/moff-gui/wiki/images/4_snap_small.png)](https://github.com/compomics/moff-gui/wiki/images/4_snap.PNG) |

(Click on a figure to see the full size version)





# Introduction #
moFF-gui a java-based gui for moFF (modest Feature Finder), a fast and light tool for the quantification of peptides directly from Thermo RAW files. moFF-gui is a platform independent (windows and linux) and it is fully integrated with the peptide-shaker output.

[Go to top of page](#moff-gui)



# Read me #
  * [Minimum Requirements](#minimum-requirements)
  * [Input Data](#input-data)
  * [Sample Data](#sample-data)
  * [moFF Parameters and Options](#moff-parameters-and-options)
  * [moFF Output](#moff-output)


## Minimum Requirements ##

Java 8 is request to run the gui.

Required python libraries :
- Python 2.7
- pandas  > 0.17.
- numpy > 1.10.0
- argparse > 1.2.1 
- scikit-learn > 0.17

moFF is based on separate program (txic or txic.exe) that allow the reading of the Thermo RAW data 
The program is compatibale with the raw file of all the Orbitrap and triple quadrupole Thermo machines. 
For the moment it does not work with the Thermo Fusion machine.

[Go to top of page](#moff-gui)

--- 
##Input Data

moFF-GUI to perform the quantification needs two type of informations:
 - Thermo RAW file 
 - MS2 identified peptide information

The MS2 identified peptides should be given in two different ways:
- The output of peptide-shaker search as cpsx file
- A tab-delimited file where for each  peptide the minimun required information are speficied

The tab-delimited file that contains the list of the MS2 identified peptides (you can use any search engines) must contains the following information for all the peptides:
  - 'peptide' : sequence of the peptide
  - 'prot': protein ID 
  - 'rt': peptide retention time  ( The retention time must be specified in second )
  - 'mz' : mass over charge
  - 'mass' : mass of the peptide
  - 'charge' : charge of the ionized peptide

For the cpsx file, the user must also load the respective mgf file and database search (fasta file) used in PeptideShaker in order to let the gui generates the right input file for moFF.
A tutorial about the use of PeptideShaker can be found at <http://compomics.com/bioinformatics-for-proteomics/>

In case the tab-delimited file provided by the user contains also other fields (i.e modifications,petides length), those will remain in the result output.

[Go to top of page](#moff-gui)

---
###Sample Data

The sample data can be found in this [folder] (genesis.ugent.be/uvpublicdata/moff_sample_data.zip) that contains the cpsx and the raw files related to 3 runs of CPTAC Study 6 (Paulovich, MCP Proteomics, 2010).
The cpsx files contain the result of tandem and msgf+ runned in SearchGui/PeptidesShaker. We also provide the search database (.fasta) and the mgf file for the 3 runs used in PeptideShaker. 
In order to use the cpsx files you need to specify in the gui where the PeptideShaker jar is stored.

[Go to top of page](#moff-gui)

---
## moFF Parameters and Options 

moFF  can be run on:
- Apex mode : moFF will extract only the apex peak for the MS2 input peptides
- Match-between-run (MBR) mode : Using all the replicates, the approach trys to match unidenfied peptides across all the run in order to  increase the number of quantified peptides.

In the apex mode , the parameter that you can edit are :
 - precursor match tollerance of the machine in ppm
 - xic retention time window (minute). Default value is 5 min.
 - peak retention time window (minute). Default value is 0.2 min.

In the match-between-run some more parameters are requested:
 - 	matched peak retention time window (minute). Default value is 0.4 
 -  combination weighting (on/off)
 - 	filter outlier and width of the filter (higher value mean less filtering effect)
 - 	

The match-between-run uses all the shared peptides among the replicates to train the RT prediction models as defoult option. In case the user wants to use a specific set of peptides insted of all the shared peptides as training sets it is possible  specify them in tab delimited formats (two fields peptides and mass).


[Go to top of page](#moff-gui)

---
## moFF Output

The for each input file, the ouput is a tab delimeted file (with the same name of the input raw file) that contains the apex intensity values and some other information.  For each output file produced by the apex module  a log file is also provided. The match-between-runs produces a separate log file where all the information about all the trained linear model are showed. 
The log files and the output files are in the output folder specified by the user at the beginning. 

Description of the fields added by moFF in the output file:

  - *rt_peak* : contains the rt in seconds where the apex peak has been found
  - *SNR* :  Signal-to-noise  of the peak intensity. Higher values means that peak intensity is far from the noise level 
  - *log_L_R*' :  this gives you the shape of the peaks. Values around 0  mean that the peak is centered wheres  log_L_R > 0 and log_L_R   means  respectively right and   means left skewed. 
  - *log_int* : log 2 transformed MS1 intensity 
  - *intensity* :  MS1 intensity
  - *lwhm* : descending on the left side peak is the first rt value  where the intensity  is at least the 50% of thee apex peak intensity (left width half maximun of the signal in seconds )
  - *rwhm* : descending on the right side of the peak  is the first rt value where the intensity is at least the 50% of thea apex peak ( right width half maximun of the signal in seconds)
  - *5p_noise* : 5th percentile of the intensity values contained in the XiC. This value is used for the *SNR* computation
  - *10p_noise* :  10th percentile of the intensity values contained in the XiC.
  - *code_unique* : this field is concatenation of the peptide sequence and mass values. It is used by moFF during the match-between-runs.
 



[Go to top of page](#moff-gui)
