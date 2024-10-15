// src/web.ts
import { WebPlugin } from '@capacitor/core';
import type { StepCounterPlugin } from './definitions';

export class StepCounterWeb extends WebPlugin implements StepCounterPlugin {
  
  async start(): Promise<void> {
    console.log("Start");
  }

  async getStepCount(): Promise<{ steps: number }> {
    console.log("Stepcount");
    const steps = 50;
    return { steps }; 
  }

  async stop(): Promise<void> {
    console.log("Stop!");
  }
  
}
