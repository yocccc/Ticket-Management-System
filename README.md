# Ticket Management System

CLIで動作するチケット管理システムです。

## ユーザーの種類

このシステムには2種類のユーザーが存在します。
- **管理者 (Admin)**: コンサートや価格の管理が可能
- **顧客 (Customer)**: コンサートチケットの予約が可能

---

## 機能一覧

### 管理者の操作

管理者は以下の操作を行うことができます。
- すべてのコンサートを表示
- コンサートの座席価格を表示・更新
- コンサートの予約状況を表示
- コンサートの総支払い額を確認

### 顧客の操作

顧客は以下の操作を行うことができます。
- コンサートの座席価格を表示
- 座席レイアウトを表示
- 複数の座席を予約
- 過去の予約詳細を表示

---

## ファイル構成

- **concert.csv**: すべてのコンサートのデータがカンマ区切り形式で含まれています。
- **customer.csv**: すべての現在の顧客のデータがカンマ区切り形式で含まれています。
- **bookings.csv**: すべての顧客による予約データがカンマ区切り形式で含まれています。
- **venue_default.txt**: 会場のデフォルトレイアウトを含むファイル。
- **venue_mcg.txt**: MCGという名の会場レイアウトを含むファイル。
- **venue_marvel.txt**: Marvelという名の会場レイアウトを含むファイル。

> **注意**: 会場ファイルは変更可能です。指定がない場合、`venue_default.txt`が使用されます。

---

## 顧客モード

顧客モードでプログラムを実行するには以下のコマンドを使用します。

```bash
$ java TicketManagementEngine --customer <顧客ID> <パスワード> <customer.csv> <concert.csv> <bookings.csv> [<会場ファイル>]
```

### コマンドライン引数

1. **--customer**: プログラムが顧客モードで実行されることを指定。
2. **顧客IDとパスワード**: オプション。指定がない場合は新しい顧客IDが生成されます。
3. **CSVファイルのパス**: `customer.csv`, `concert.csv`, `bookings.csv`の順に指定。
4. **会場ファイルのパス**: オプション。指定がない場合、デフォルトの会場ファイルが使用されます。

### ユースケース

#### 1. 正しい顧客IDとパスワードでのログイン
パスワードと顧客IDはcustomer.csvファイルに存在し、一致する必要があります。

```bash
$ java TicketManagementEngine --customer 1 abc@1 assets/customer.csv assets/concert.csv assets/bookings.csv assets/venue_mcg.txt
```

出力例:

```plaintext
Welcome John Doe to Ticket Management System

 ________  ___ _____ 
|_   _|  \/  |/  ___|
  | | | .  . |\ `--. 
  | | | |\/| | `--. \
  | | | |  | |/\__/ /
  \_/ \_|  |_\____/ 

Select a concert or 0 to exit
---------------------------------------------------------------------------------------------------------------------------
#    Date           Artist Name    Timing         Venue Name                    Total Seats    Seats Booked   Seats Left     
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900           MCG                           224            5              219            
2    2024-10-04     Taylor Swift   1900           MARVEL                        143            5              138            
---------------------------------------------------------------------------------------------------------------------------
>
```

#### 2. 不正な顧客IDまたはパスワード

- **不正なパスワード**: `Incorrect Password. Terminating Program`
- **不正な顧客ID**: `Customer does not exist. Terminating Program`

#### 3. 顧客IDとパスワードなしでのログイン

顧客名とパスワードの入力を求められ、新しい顧客IDが生成されます。

```plaintext
Enter your name: John Doe
Enter your password: abc#1234
```

---

## 顧客操作メニュー

### メニューオプション

1. **チケットの価格を見る**
2. **座席レイアウトを見る**
3. **座席を予約する**
4. **予約詳細を見る**
5. **終了する**

#### 1. チケット価格の表示

座席エリアごとの価格を表示します。

```plaintext
----------  SEATING ----------
Left Seats:   199.0
Center Seats: 199.0
Right Seats:  259.0
------------------------------
---------- STANDING ----------
Left Seats:   99.0
Center Seats: 99.0
Right Seats:  149.0
------------------------------
----------      VIP ----------
Left Seats:   359.0
Center Seats: 359.0
Right Seats:  499.0
------------------------------
```

#### 2. 座席レイアウトの表示

予約済み座席は「X」で表示されます。

```plaintext
V1 [1][2][3][4][5] [6][7][8][9][10][11] [12][13][14][15][16] V1
V3 [1][2][X][X][X] [6][7][8][9][10][11] [12][13][14][15][16] V3
```

#### 3. 座席の予約

通路番号と座席番号を指定して予約します。

```plaintext
Enter the aisle number: T1
Enter the seat number: 5
Enter the number of seats to be booked: 4
```

#### 4. 予約詳細の表示

選択されたコンサートの顧客の予約履歴を表示します。

```plaintext
Bookings
---------------------------------------------------------------------------------------------------------------------------
Id   Concert Date   Artist Name    Timing    Venue Name     Seats Booked   Total Price
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900      MCG            3              1217.0
2    2024-10-01     Taylor Swift   1900      MCG            2              518.0     
3    2024-10-01     Taylor Swift   1900      MCG            4              396.0


Ticket Info
############### Booking Id: 1 ####################
Id   Aisle Number   Seat Number    Seat Type Price     
##################################################
1    3              3              VIP       359.0     
2    3              4              VIP       359.0     
3    3              5              VIP       499.0     
##################################################

############### Booking Id: 2 ####################
Id   Aisle Number   Seat Number    Seat Type Price     
##################################################
1    4              7              SEATING   259.0     
2    4              8              SEATING   259.0     
##################################################

############### Booking Id: 3 ####################
Id   Aisle Number   Seat Number    Seat Type Price     
##################################################
1    1              5              STANDING  99.0      
2    1              6              STANDING  99.0      
3    1              7              STANDING  99.0      
4    1              8              STANDING  99.0      
##################################################
```

#### 5. 終了

現在のコンサートメニューから退出し、コンサート選択画面が再度表示されます。ここで、同じコンサートまたは別のコンサートで操作を続行するか、プログラムを終了するために「0」を選択することができます。


---

## 管理者モード

管理者モードでプログラムを実行するには以下のコマンドを使用します。

```bash
$ java TicketManagementEngine --admin <customer.csv> <concert.csv> <bookings.csv> [<会場ファイル>]
```
```bash
$ java TicketManagementEngine --admin assets/customer.csv assets/concert.csv assets/bookings.csv assets/venue_mcg.txt
```


### メニューオプション

1. **すべてのコンサートの詳細を表示**
2. **チケット料金を更新**
3. **予約詳細を表示**
4. **コンサートの総支払い額を表示**
5. **終了する**

#### 1. すべてのコンサートの詳細を表示

すべてのコンサートの詳細が表示されます。

```plaintext
---------------------------------------------------------------------------------------------------------------------------
#    Date           Artist Name    Timing         Venue Name                    Total Seats    Seats Booked   Seats Left     
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900           MCG                           224            5              219            
2    2024-10-04     Taylor Swift   1900           MARVEL                        143            5              138            
---------------------------------------------------------------------------------------------------------------------------
```

#### 2. チケット料金の更新

座席ゾーンを選択し、エリアごとの価格を更新できます。

```plaintext
Select a concert or 0 to exit
----------  SEATING ----------
Left Seats:   199.0
Center Seats: 199.0
Right Seats:  259.0
------------------------------
Enter the zone : VIP, SEATING, STANDING: SEATING
Left zone price: 200
Centre zone price: 225
Right zone price: 220
```

#### 3. 予約詳細の表示

すべての顧客の予約詳細を確認できます。コンサートを選択した後、以下のようなデータが表示されます。

```plaintext
Bookings
---------------------------------------------------------------------------------------------------------------------------
Id   Concert Date   Artist Name    Timing    Venue Name     Seats Booked   Total Price
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900      MCG            3              1217.0    
2    2024-10-01     Taylor Swift   1900      MCG            2              518.0     
---------------------------------------------------------------------------------------------------------------------------

Ticket Info
############### Booking Id: 1 ####################
Id   Aisle Number   Seat Number    Seat Type Price     
##################################################
1    3              3              VIP       359.0     
2    3              4              VIP       359.0     
3    3              5              VIP       499.0     
##################################################

############### Booking Id: 2 ####################
Id   Aisle Number   Seat Number    Seat Type Price     
##################################################
1    4              7              SEATING   259.0     
2    4              8              SEATING   259.0     
##################################################
```

#### 4. コンサートの総支払い額を表示

コンサートで受け取った総支払い額を表示します。

```plaintext
Select a concert or 0 to exit
---------------------------------------------------------------------------------------------------------------------------
#    Date           Artist Name    Timing         Venue Name                    Total Seats    Seats Booked   Seats Left     
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900           MCG                           224            5              219            
2    2024-10-04     Taylor Swift   1900           MARVEL                        143            5              138            
---------------------------------------------------------------------------------------------------------------------------
> 1
Total Price for this concert is AUD 1735.0
```

#### 5. 終了する

プログラムを終了し、すべてのデータがCSVファイルに保存されます。
