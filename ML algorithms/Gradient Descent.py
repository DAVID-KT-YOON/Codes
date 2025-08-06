import numpy as np

def preprocessing_train_data():
    arr = []
    y_vals = []
    #Opening train data as f
    f = open("train.csv", "r")
    f.readline()  #getting rid of labels.
    line = f.readline() 

    #Preparation to change all data to int and oldpeak data to float
    while(line):
        line = line.split(",")
        for i in range(len(line)):
            if (i == 9):
                line[i] = float(line[i])
            else:
                line[i] = int(line[i])
        
        #Preprocessing process: generating y and arr that has y intercept preprocessing data.
        y_vals.append(line.pop())
        line.insert(0, 1)
        arr.append(line)
        line = f.readline()
    #Creating matrix X
    x_vals = np.array(arr)

    #Closing out the file
    f.close()

    return x_vals ,y_vals

#This function processes test data
def preprocessing_test_data():
    e = open("test.csv","r")
    e.readline()
    line = e.readline()
    arr = []

    while(line):
        line = line.split(",")
        for i in range(len(line)):
            if (i == 9):
                line[i] = float(line[i])
            else:
                line[i] = int(line[i])
        line.insert(0,1)
        arr.append(line)
        line = e.readline()
    x_vals = np.array(arr)

    return x_vals
def feature_scaling():
    arr = []
    #Opening train data as f
    a = open("train.csv", "r")
    a.readline()  #getting rid of labels.
    line = a.readline() 

    #Preparation to change all data to int and oldpeak data to float
    while(line):
        line = line.split(",")
        for i in range(len(line)):
            if (i == 9):
                line[i] = float(line[i])
            else:
                line[i] = int(line[i])
        
        #Preprocessing process: generating y and arr that has y intercept preprocessing data.
        line.pop()
        arr.append(line)
        line = a.readline()
    #Creating matrix X
    X = np.array(arr)

    mean = np.mean(X, axis = 0)
    std_dev = np.std(X, axis = 0)

    X_scaled = []
    for i in range(len(X)):
        arr = []
        for j in range(len(X[i])):
            arr.append((X[i][j] - mean[j]/std_dev[j]))
        X_scaled.append(arr)
    X_scaled = np.array(X_scaled)
    return X_scaled


#This function produces predicion.csv
def processing_prediction_data(X , w):
    prediction = []
    fd = open("prediction.csv","w")

    for i in range(len(X)):
        prediction.append(sigmoid(np.dot(w,X[i])))
        
    for i in range(len(prediction)):
        if prediction[i] >= .5:
            fd.write("1\n")
        else:
            fd.write("-1\n")
    return None

#Sigmoid function
def sigmoid(z):
    return (1/(1+np.exp(-z)))

#Gradient function
def gradient(X,y,w):
    sum = 0
    for i in range(len(X)): 
        sum += (y[i] * X[i]) / (1 + np.exp(y[i] * np.dot(w,X[i])))
    return sum / -len(X)

#Cross-entropy error
def cross_Entropy_Error(X,y,w):
    sum = 0
    for i in range(len(X)):
        sum += np.log(1 + np.exp(-y[i] * np.dot(w, X[i])))
    return sum/len(X)

#Classification error
def classification_Error(X,y,w):
    count = 0
    for i in range(len(X)):
        prediction = sigmoid(np.dot(w,X[i]))
        if prediction >= .5:
            prediction = 1
        else:
            prediction = -1
        
        if prediction != y[i]:
            count +=1
    return count/len(X)

#Gradient descent 
def gradient_Descent(X, y, eta, iteration, threshold = 10**-3):

    #Preprocessing w: w(0) at t = 1
    w = np.zeros(len(X[0]))
    
    for i in range(iteration):
        #Calculate gradient
        g = gradient(X,y,w)
        #If all of gradient is below threshold, converging thus break
        if np.all( np.abs(g)) < threshold:
            print("converged")
            break
        
        w -= eta * g
    #Cross entropy error 
    cee =cross_Entropy_Error(X,y,w)
    #Classification error 
    cle = classification_Error(X,y,w)

    return w,cee,cle

def main():
    #DO NOT TOUCH
    #Pre-process the X_train data and y 
    X_train, y = preprocessing_train_data()
    X_test = preprocessing_test_data()
    
    #1
    w , cee , cle = gradient_Descent(X_train,y,10**-5, 1000000)
    processing_prediction_data(X_test, w)
    print("weights =", w)
    print("cross entropy error =",cee)
    print("classification error =",cle)
    ###1

    #2
    # X_Scaled = feature_scaling()
    # w_Scaled , cee_Scaled , cle_Scaled = gradient_Descent(X_Scaled, y, 10**-6, 1000000 , 10^-6)
    # print("weights_Scaled =", w_Scaled)
    # print("cross entropy error_Scaled =",cee_Scaled)
    # print("classification error_Scaled =",cle_Scaled)
    ###2

if __name__ == "__main__":
    main()