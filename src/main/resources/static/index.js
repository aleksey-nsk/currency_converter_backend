angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8082/api/v1'; // dev-профиль
    console.log("contextPath: " + contextPath);

    $scope.getCurrentRate = function () {
        const url = contextPath + '/rate';
        console.log("Method getCurrentRate(), url: " + url);
        $http.get(url)
                .then(function (resp) {
                    $scope.Rates = resp.data;
                });
    };

    $scope.generatePageIndexes = function (startPage, endPage) {
        console.log("Method generatePageIndexes(), startPage=" + startPage + ", endPage=" + endPage);
        let arr = [];
        for (let i = startPage; i <= endPage; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.getConvertPage = function (pageIndex = 1) {
        const url = contextPath + '/convert';
        console.log("Method getConvertPage(), url: " + url);

        $http({
            url: url,
            method: 'GET',
            params: {
                pageIndex: pageIndex
            }
        }).then(function (response) {
            $scope.ConvertPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ConvertPage.totalPages) {
                maxPageIndex = $scope.ConvertPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePageIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.getStatistics = function () {
        const url = contextPath + '/statistics';
        console.log("Method getStatistics(), url: " + url);
        $http.get(url)
                .then(function (resp) {
                    $scope.Statistics = resp.data;
                });
    };

    $scope.fillPage = function () {
        $scope.getCurrentRate();
        $scope.getConvertPage();
        $scope.getStatistics();
    };

    $scope.doConvert = function () {
        const url = contextPath + '/convert';
        console.log("Method doConvert(), url: " + url);
        console.log($scope.NewConvert);

        if ($scope.NewConvert === undefined) {
            $scope.NewConvert = {}; // пустой объект
            console.log($scope.NewConvert);
        }

        $http.post(url, $scope.NewConvert)
                .then(function (resp) {
                    $scope.Result = resp.data;
                    $scope.fillPage();
                });
    };

    $scope.fillPage();

});
