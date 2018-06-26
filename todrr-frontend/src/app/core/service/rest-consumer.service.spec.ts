import { TestBed, inject } from '@angular/core/testing';

import { RestConsumerService } from './rest-consumer.service';

describe('RestConsumerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RestConsumerService]
    });
  });

  it('should be created', inject([RestConsumerService], (service: RestConsumerService) => {
    expect(service).toBeTruthy();
  }));
});
