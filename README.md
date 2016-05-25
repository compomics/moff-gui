# moFF-gui #

* [Introduction](#introduction)
* [Read Me](#read-me)




|   |   |   |
| :------------------------- | :---------------: | :--: |
| [![download](https://github.com/compomics/moff-gui/wiki/images/button_3.png)](https://github.com/compomics/moff-gui/releases/download/0.1.0-beta/moff-gui-0.1.0-beta.zip) | *v0.1.0-beta - All platforms* | [ReleaseNotes](https://github.com/compomics/moff-gui/releases/tag/0.1.0-beta) |


|  |  |  |
|:--:|:--:|
| [![](https://github.com/compomics/moff-gui/wiki/images/1_snap_small.png)](hhttps://github.com/compomics/moff-gui/wiki/images/1_snap.PNG) 
| [![](https://github.com/compomics/moff-gui/wiki/images/2_snap_small.png)](hhttps://github.com/compomics/moff-gui/wiki/images/2_snap.PNG) 
(Click on a figure to see the full size version)





# Introduction #
moFF-gui a java-based gui for moFF (modest FEature Finder), a fast and light tool for the quantification of peptides directly from Thermo RAW files. moFF-gui is a platform independent(windows and linux) and it is fully integrated with the peptide-shaker output.

[Go to top of page](#moff-gui)



# Read me #
  * [Minimum Requirements](#minimum-requirements)
  * [Input data](#input-data)
  * [moFF parameters and options](#moff-parameters-and-options)
  * [moFF output](#moff-output)


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
##Input data

moFF-GUI to perform the quantification needs two type of informations:
 - Thermo RAW file 
 - MS2 identified peptide information

The MS2 identified peptides should be given in two different ways:
- The output of peptide-shaker search as cpx file
- A tab-delimited file where for each  peptided and the minimun required information are speficied

The tab-delimited file that contains the list of the MS2 identified peptides (you can use any search engines) must contains the following information for all the peptides:
  - 'peptide' : sequence of the peptide
  - 'prot': protein ID 
  - 'rt': peptide retention time  ( The retention time must be specified in second )
  - 'mz' : mass over charge
  - 'mass' : mass of the peptide
  - 'charge' : charge of the ionized peptide

For the cps file, the user must also load the corrispetive respective mgf file and database search (fasta file )  used in Peptide Shaker in order to let the gui automatically  generates the right input file for moFF.

In case the tab-delimited file insered by the user contains also other fields (i.e modifications,petides length), those will remain in the result output.

[Go to top of page](#moff-gui)

---
## moFF parameters and options 

moFF  can be run on:
- Apex mode : moFF will extract only the apex peak for the MS2 input peptides
- Matching between run (MBR) mode : Using all the runs of an experiments  a matching run is performed to increase the number of quantified peptides.

In the apex mode , the parameter that you can edit are :
 - precursor match tollerance of the machine in ppm
 - xic retention time window (minute). Default value is 5 min.
 - peak retention time window (minute). Default value is 0.2 min.

In the matching-between-run methos some more parameters are requested:
 - 	matched peak retention time window (minute). Default value is 0.4 
 -  conbination weighting
 - 	filter outlier and width of the filter (higher value mean less filtering effect)



[Go to top of page](#moff-gui)

---
## moFF output

The for each input file, the ouput is a tab delimeted file (with the same name of the input raw file) that contains the apex intensity values and some other information.  For each output file produced by the apex module  a log file is also provided. The matching-between-runs produces a separate log file where all the information about all the trained linear model are showed. 
The log files and the output files are in the output folder specified by the user at the beginning. 

Description of the fields added by moFF in the output file:

  - rt_peak : apex peak rt
  - SNR :  SNR of the peak 
  - log_L_R' :  shape of the peak (around 0  means  centered,  log_L_R > 0 means right skewed, log_L_R <0 means left skewed ) 
  - log_int' : log transformed MS1 intensity 
  - intensity :  MS1 intensity
  - lwhm" left : width half maximun of the signal in seconds
  - rwhm" right:  width half maximun of the signal in seconds
 



[Go to top of page](#moff-gui)
