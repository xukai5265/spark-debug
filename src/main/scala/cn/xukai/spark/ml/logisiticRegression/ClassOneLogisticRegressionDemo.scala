//package cn.xukai.spark.ml.logisiticRegression
//
///**
//  * Created by kaixu on 2018/1/8.
//  */
//object ClassOneLogisticRegressionDemo extends App{
//  //加载猫的图像的数据集
//  val catData: DlCollection[Cat] = CatDataHelper.getAllCatData
//
//  //获取training set和test set
//  val (training, test) = catData.split(0.8)
//
//
//  //分别获取训练集和测试集的feature和label
//  val trainingFeature = training.getFeatureAsMatrix
//  val trainingLabel = training.getLabelAsVector
//  val testFeature = test.getFeatureAsMatrix
//  val testLabel = test.getLabelAsVector
//
//  //初始化LR的算法模型
//  val lrModel: LogisticRegressionModel = new LogisticRegressionModel()
//    .setLearningRate(0.005)
//    .setIterationTime(3000)
//
//  //用训练集的数据训练算法
//  val trainedModel: LogisticRegressionModel = lrModel.train(trainingFeature, trainingLabel)
//
//  //测试算法获得算法优劣指标
//  val yPredicted = trainedModel.predict(testFeature)
//  val trainYPredicted = trainedModel.predict(trainingFeature)
//
//  val testAccuracy = trainedModel.accuracy(testLabel, yPredicted)
//  val trainAccuracy = trainedModel.accuracy(trainingLabel, trainYPredicted)
//  println("\n The train accuracy of this model is: " + trainAccuracy)
//  println("\n The test accuracy of this model is: " + testAccuracy)
//
//  //对算法的训练过程中cost与迭代次数变化关系进行画图
//  val costHistory = trainedModel.getCostHistory
//  PlotUtils.plotCostHistory(costHistory)
//}
