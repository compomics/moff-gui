# moFF-gui #

* [Introduction](#introduction)
* [Read Me](#read-me)




|   |   |   |
| :------------------------- | :---------------: | :--: |
| [![download](https://github.com/compomic/xx/wiki/images/download_button.png)](link) | *v1.10.2 - All platforms* | [ReleaseNotes](https://github.com/compomics/xx/wiki/ReleaseNotes) |


# Introduction #
moFF-gui a java-based gui for moFF (modest FEature Finder), a fast and light tool for the quantification of peptides directly from Thermo RAW files. moFF-gui is a platform independent(windows and linux) and it is fully integrated with the peptide-shaker output.

[Go to top of page](#moff-gui)



# Read me #
  * [Minimum Requirements](#minimum-requirements)
  * [Input data](#input-data)
  * [moFF parameters and options](#moff-parameters-and-options)


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

moFF-GUI to perform the APEX quantification needs two  type of informations:
 - RAW file 
 - MS2 identified peptide information
The MS2 identified peptides should be given in two different ways:
- The output of peptide-shaker search in cpx file
- A tab-delimited file where each peptided and its information are speficied

The user can use the moFF-gui with peptide-shaker output or use its idenfied result obtanied with any search engines.
The tab-delimite files that contain the list of the MS2 identified peptides (you can use any search engines) must contains the information showed in *moFF_setting.property* for each peptide. The minimun specificic requirements of the input files are:
- tab delimited file
- the header of the input file should contain the following the fields  and columnns names :  
  - 'peptide' : sequence of the peptide
  - 'prot': protein ID 
  - 'rt': peptide retention time  ( The retention time must be specified in second )
  - 'mz' : mass over charge
  - 'mass' : mass of the peptide
  - 'charge' : charge of the ionized peptide

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

The for each input file, the ouput consinst in a tab delimeted file that contain the apex intensity value and some other information. The log file and the output file are in the output folder specified by the user at the beginning.

[Go to top of page](#moff-gui)
