from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import pandas as pd
import numpy as np

def preprocessing_train_data():
    # setting of train data. feature, first column is label, everything else are features.
    f = open("train_data.txt", "r")
    datas = []
    line = f.readline()
    while(line):
        data = list(map(float, line.strip().split(",")))
        datas.append(data) 
        line = f.readline()
    df = pd.DataFrame(datas)
    feat = df.iloc[:,1:]
    lab = df[0]
    return feat, lab

def preprocessing_test_data():
    # setting of test data. 
    d = open("test_data.txt", "r")
    test_datas = []
    line = d.readline()
    while(line):
        data = list(map(float, line.strip().split(",")))
        test_datas.append(data) 
        line = d.readline()
    return np.array(test_datas)

def create_sample(X,y,weights):
    #indices of resampled example out of size Len of feature.
    resampled_indices = np.random.choice(len(X), size=len(X), replace=True, p=weights)

    #organization of resampled indices.
    X_resampled = X.iloc[resampled_indices]
    y_resampled = y.iloc[resampled_indices]
    
    return X_resampled, y_resampled

def adaboost(feature, label, iteration):
    w = (np.ones(len(label)) / len(label))
    stumps = []
    alphas = []
    for i in range(iteration):
        # Create training set by sampling with replacement from D according to w
        X, y,= create_sample(feature,label,w)   
        stump = DecisionTreeClassifier(max_depth=1, criterion="gini")
        # Train a base classifier Ci on Di   
        stump.fit(X, y)
        # Apply Ci to all instances in the original training set, D.
        predictions = stump.predict(feature)
        # Calculate the weighted error
        error = np.sum(w *(predictions != label))
        if error > 0.5:
            #Reset the weights for all n instances.
            w = np.ones(len(label)) / len(label)
            continue
        # Calculate alphai
        alpha = (.5) * np.log((1 - error) / error)
        # Update of weight
        for j in range(len(w)):
            if predictions[j] == label[j]:
                w[j] *= np.exp(-alpha)
            else:
                w[j] *= np.exp(alpha)
        #Normalize the weight
        w /= np.sum(w)
        #keep the stumps and alphas
        stumps.append(stump)
        alphas.append(alpha)

    return stumps,alphas

def predict(X, stumps, alphas):
    add5 = 0
    add3 = 0
    for i in range(len(stumps)):
        predictions = stumps[i].predict(X)
        for j in range(len(predictions)):
            add5 += alphas[i]*(predictions[j] == 5)
            add3 += alphas[i]*(predictions[j] == 3)


    return 5 if add5 > add3 else 3
     
def main():

    feature, label = preprocessing_train_data()
    stumps, alphas = adaboost(feature,label, 100)

    test_data = preprocessing_test_data()
    predictions = []
    for x in test_data:
        x = np.array(x).reshape(1, -1)
        pred = predict(x,stumps,alphas)
        predictions.append(pred)
    e = open("predictions.txt", "w")
    for i in range(len(predictions)):
        if predictions[i] == 3:
            e.write("3\n")
        else:
            e.write("5\n")
    
    #test error without pruning
    X_train, X_test, y_train, y_test = train_test_split(feature, label, test_size=0.2, random_state=5)
    tree = DecisionTreeClassifier()
    tree.fit(X_train, y_train)
    y_predict = tree.predict(X_test)
    accuracy = 1 - accuracy_score(y_predict,y_test)
 
    #training set error
    predictions_training = []
    feature = np.array(feature)
    for y in feature:
        y = np.array(y).reshape(1, -1)
        pred = predict(y,stumps,alphas)
        predictions_training.append(pred)
    training_set_error = 1 - accuracy_score(predictions_training, label)
    
    unweighted = np.ones(len(alphas))
    #training set error unweighted
    predictions_training1 = []
    for y in feature:
        y = np.array(y).reshape(1, -1)
        pred = predict(y,stumps,unweighted)
        predictions_training1.append(pred)
    training_set_error1 = 1 - accuracy_score(predictions_training1, label)

    print(training_set_error)
    print(training_set_error1)
    print(accuracy)


if __name__ == "__main__":
    main()