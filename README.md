## 概要

spring-cloud-starter-aws-messaging を使ったサンプル。

## 特徴

* locakstack を使い、AWS に接続せずローカルで SNS, SQS を使って稼働できる。

## localstack を使った確認

1. docker-compose で locakstack コンテナを起動する。

```
docker-compose up -d
```

2. アプリケーションを local プロファイルで起動する。

```
SPRING_PROFILES_ACTIVE=local gradle bootRun
```

## 動作検証

SNS トピック、SQS キューを作成する。

```
http://localhost:8080/create
```

SNS トピック、SQS キューを削除する。

```
http://localhost:8080/destroy
```

SNS にメッセージを送信する。
* SNS トピック、SQS キューがない場合は自動作成する。
* body パラメータを省略したときは body という文字列が送信される。

```
http://localhost:8080/send
http://localhost:8080/send?body=abc
```

キューからメッセージを取り出す。
* SNS トピック、SQS キューがない場合は自動作成する。

```
http://localhost:8080/receive
```
