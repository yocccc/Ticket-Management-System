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

```bash
$ java TicketManagementEngine --customer 1 abc@1 assets/customer.csv assets/concert.csv assets/bookings.csv assets/venue_mcg.txt
```

出力例:

```plaintext
Welcome Trina Dey to Ticket Management System

 ________  ___ _____ 
|_   _|  \/  |/  ___|
  | | | .  . |\ `--. 
  | | | |\/| | `--. \
  | | | |  | |/\__/ /
  \_/ \_|  |_\____/ 

Select a concert or 0 to exit
```

#### 2. 不正な顧客IDまたはパスワード

- **不正なパスワード**: `Incorrect Password. Terminating Program`
- **不正な顧客ID**: `Customer does not exist. Terminating Program`

#### 3. 顧客IDとパスワードなしでのログイン

顧客名とパスワードの入力を求められ、新しい顧客IDが生成されます。

```plaintext
Enter your name: Jane Doe
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

顧客の予約履歴を表示します。

```plaintext
Bookings
---------------------------------------------------------------------------------------------------------------------------
Id   Concert Date   Artist Name    Timing    Venue Name     Seats Booked   Total Price
---------------------------------------------------------------------------------------------------------------------------
1    2024-10-01     Taylor Swift   1900      MCG            3              1217.0
```

---

## 管理者モード

管理者モードでプログラムを実行するには以下のコマンドを使用します。

```bash
$ java TicketManagementEngine --admin <customer.csv> <concert.csv> <bookings.csv> [<会場ファイル1>] [<会場ファイル2>]
```

### メニューオプション

1. **すべてのコンサートの詳細を表示**
2. **チケット料金を更新**
3. **予約詳細を表示**
4. **コンサートの総支払い額を表示**
5. **終了する**

#### チケット料金の更新

座席エリアごとに価格を変更できます。

```plaintext
Select a concert or 0 to exit
Enter new price for Left Seats: 299.0
Enter new price for Right Seats: 399.0
```

---

## 注意事項
- プログラムを終了するとすべてのデータがCSVファイルに保存されます。
- 各CSVファイルのフォーマットが正しいことを確認してください。
