import numpy as np
import matplotlib.pyplot as plt

def plotmake(filename, tab):
    data = np.loadtxt(filename, delimiter=',', skiprows=1)
    plt.plot(data[:,0], 'b', label='apriori')
    plt.plot(data[:,1], 'r', label='fpgrowth')
    
    plt.xlabel(tab[filename])
    plt.ylabel('Time (ms)')

    plt.legend()
    plt.title(filename[:len(filename)-4])
    plt.savefig(filename[:len(filename)-4] + '.png')
    #plt.show()
    plt.close()

if __name__=="__main__":
    # read data from csv files in benchmark_results directory
    # and plot them

    files_names = {}
    files_names["benchmark_results/benchmarkNbVariable_0-1freq.csv"] = "Number of variables * 2"
    files_names["benchmark_results/benchmarkNbVariable_0-5freq.csv"] = "Number of variables * 2"
    files_names["benchmark_results/benchmarkNbVariable_0-9freq.csv"] = "Number of variables * 2"
    
    files_names["benchmark_results/benchmarkNbTransaction_0-1freq.csv"] = "Number of transactions * 2"
    files_names["benchmark_results/benchmarkNbTransaction_0-5freq.csv"] = "Number of transactions * 2"
    files_names["benchmark_results/benchmarkNbTransaction_0-9freq.csv"] = "Number of transactions * 2"
    
    files_names["benchmark_results/benchmarkAll_0-1freq.csv"] = "All"
    files_names["benchmark_results/benchmarkAll_0-5freq.csv"] = "All"
    files_names["benchmark_results/benchmarkAll_0-9freq.csv"] = "All"

    for file_name in files_names:
        plotmake(file_name, files_names)
