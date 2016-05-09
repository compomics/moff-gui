# moFF-gui #

* [Introduction](#introduction)
* [Read Me](#read-me)




|   |   |   |
| :------------------------- | :---------------: | :--: |
| [![download](https://github.com/compomic/xx/wiki/images/download_button.png)](link) | *v1.10.2 - All platforms* | [ReleaseNotes](https://github.com/compomics/xx/wiki/ReleaseNotes) |


# Introduction #
moFF-gui a java-based gui for moFF (modest FEature Finder), a fast and light tool for the quantification of peptides directly from Thermos RAW files. moFF-gui is platform independent  and it is fully integrated with the peptide-shaker output.

[Go to top of page](#moff-gui)



# Read me #
* [Minimum Requirements](#minimum-requirements)
* [Input data](#input-data)
* [moFF parameters and options](#moFF-parameters-and-options)


## Minimum Requirementss ##

Java 8 is request to run the gui.

Required python libraries :
- Python 2.7
- pandas  > 0.17.
- numpy > 1.10.0
- argparse > 1.2.1 
- scikit-learn > 0.17

moFF is based on separate program (txic or txic.exe) that allow the reading of the Thermo RAW data 
The program is compatibale with  the raw file of all the Orbitrap and triple quadrupole Thermo machines. 
For the moment it does not work with the Thermo Fusion machine.

[Go to top of page](#moff-gui)


## Input data ## 

moFF-GUI to perform the APEX quantification needs two  type of informations:
 - RAW file 
 - MS2 identified peptide information
 - 
The MS2 identified peptides should be given in two different ways:
- The output of peptide-shaker search in cpx file
- A tab-delimited file where each peptided and its information are speficied

The user can use the moFF-gui with peptide-shaker output or use its idenfied result obtanied with any search engines.

The tab-delimite files that contain the list of the MS2 identified peptides (you can use any search engines) must contains the information showed in *moFF_setting.property* for each peptide. The minimun specificic requirements of the input files are:
- tab delimited file
- the header of the input file should contain the following the fields  and columnns names :  
-- 'peptide' : sequence of the peptide- 'prot': protein ID 
-- 'rt': retention time of peptide   ( The retention time must be specified in second )
-- 'mz' : mass over charge
-- 'mass' : mass of the peptide
-- 'charge' : charge of the ionized peptide

[Go to top of page](#moff-gui)
