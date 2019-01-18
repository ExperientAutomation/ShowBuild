****Change Inventory and ShowEvents dates****

1. ShowBuild -> Src -> Showbuild -> xls -> 'B Suite.xlsx'
2. Change dates in Sheet 'ConfigureSchedules' &  'ShowItemInput'

3. ShowBuild -> Src -> Showbuild -> xls -> 'C Suite.xlsx'
4. Change date in Sheet 'EventAdminDetails'

------------------------------------------------------
**** Change ShowCode and Environment ****

1. ShowBuild  -> Src -> -> 'Showbuild.config' -> Config. Properites

2. Declare as 'LIVE' or 'RD' or 'UAT'
3. Decalre one exisiting showcode and new showcode

Example:

environment=LIVE
existingshowcode=QAB621
showcode=NBS623

-----------------------------------------------------
**** Execution One by One ****

If new ShowCode is not Created and you want to Create ShowCode.

1. ShowBuild -> Src -> 'Create Show' package -> click on 'Create_ShowCode.java' file and click on Run.

If showcode is created already, then start from here.. Setup Registration.
2. ShowBuild -> Src -> RegOnly_Show -> execute from file which strats from 'A_' to 'K_'

Setup Housing 
3. ShowBuild -> Src -> Setup_Housing -> execute from 'A_' to 'D_'.

Push to Prod
4. ShowBuild -> Src -> PushToProd -> execute from 'A_' to 'C_'.



