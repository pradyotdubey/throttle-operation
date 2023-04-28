# throttle-operation
Problem Statement
Throttle is an important component of refrigeration cycle wherein it allows a means of reducing 
the temperature of working fluid. For the present problem, consider a n-butane stream at 
pressure �! and temperature �! that is throttled to a final pressure �". The temperature (�") or 
the quality (vapor mole fraction: �"
# ≡ �") of the exit stream are unknown.
Model the throttle process as a steady-state, steady-flow adiabatic operation with no moving 
parts. Further, you can ignore kinetic energy and potential energy changes for the flow streams.
With these assumptions, the general energy balance simplifies to: Δ�!" = 0.
Task
Develop a code in language/library of your choice (C, Matlab, Python, etc.) to answer question 
given below. A general solution approach has been discussed in the class. Some test cases will 
be provided on class notebook to allow validation of code before viva.
1. (2 mark) Determine the phase of the exit stream: subcooled liquid, liquid-vapor 
coexistence, superheated vapor.
2. (5 marks) Determine the temperature (�") of the exit stream. Further, in case the exit 
stream phase is liquid-vapor coexistence, also determine its quality, �".
3. (5 marks) Determine the Joule-Thomson coefficient, �$% at the inlet condition. Further, 
calculate an approximation for the exit temperature as �" = �! + �$%(�" − �!), 
obtained by taking �$% as a constant for the throttle operation and value fixed at the 
inlet condition value.
Parameter values
�! (�� MPa):{1.4, 1.6}; �! (in K): {200, 400}; �" = 0.1 MPa
Thermophysical properties of n-butane: tabulated data for isobars at 0.1, 1.4, 1.6 MPa given in 
excel sheet. Use linear interpolation in �, � to get a value at any given state point.
• Input parameters (�!, �!) should be defined such that their value can be provided at time 
of execution without need to recompile the code. You can either use an input file
(preferable) or take input from command line.
• Answer to Q1-3 can be printed to an output file or displayed on command line.
• If code needs recompiling / changes as input parameters are varied, marks will be scaled 
down to 50-80% of score attained.
*Evaluation and suggested solution approach on next page
CLL121 2022-23 SII Coding Exercise MM: 12
2
Evaluation
Viva Schedule: tbd
Note: Please check your code thoroughly by manual calculation, constructing test cases. Viva 
marks will be given primarily for correct answers, with only 30% credit for a case with fully 
functional code, but none of the values obtained correctly.
Solution approach
- Use equation 6.6(a,b) for both liquid and vapor phase property change. All required 
parameter values, as function of �, �, are given in isobar tables.
Parameter definitions:
Isochore derivative: ?
&'
&(@
)
Isotherm derivative: ?
&'
&)@
(
- At phase change, add or subtract the property change on phase transition. Given in 
isobar tables.
- One possible strategy to answer all three parts:
First prepare an array of enthalpy values, �(�, �), for the entire range (subcooled liquid 
to superheated vapor) at each isobar. The specific questions asked can now be easily 
answered.
You can cross-check the accuracy of your calculation by manually calculating a few 
properties such as enthalpy change of real gas, property change on phase transition by 
an orthogonal technique (say Les-Kesler correlations) as well as manual calculation by 
using given tabulated data
