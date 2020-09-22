import { Component, OnInit } from '@angular/core';
import { RestCallService } from '../restservice';
import { Config } from 'protractor';
import { error } from '@angular/compiler/src/util';

@Component({
  selector: 'app-series-view',
  templateUrl: './series-view.template.html',
  styleUrls: ['./series-view.css']
})
export class SeriesViewComponent implements OnInit {
  public title: string;
  public numberOfSeasons: number;
  public seriesList: [];

  public error: string;

  constructor(private rest: RestCallService) { }

  getAllSeries(): void {        
    this.rest.getAllSeries()
      .subscribe(
        (result: Config) => {
          console.log(result);
          
          this.seriesList = result.body;
        },
        error => {
          this.error = 'Error!';
          
        }
      )
  }

  ngOnInit(): void {
    this.getAllSeries();
  }

}
