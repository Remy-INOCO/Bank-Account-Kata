import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ITransaction } from 'src/app/models/transaction';

@Component({
  selector: 'kata-transaction-table-view',
  templateUrl: './transaction-table-view.component.html',
  styleUrls: ['./transaction-table-view.component.css']
})
export class TransactionTableViewComponent implements OnInit, AfterViewInit, OnChanges {
  displayedColumns: string[] = ['date', 'operation', 'wording', 'amount', 'balance'];
  dataSource = new MatTableDataSource<ITransaction>();
  @Input() transactions: ITransaction[];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor() { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.transactions);
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(): void {
    this.dataSource = new MatTableDataSource(this.transactions);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
