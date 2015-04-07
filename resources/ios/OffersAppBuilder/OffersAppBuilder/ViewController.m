//
//  ViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 1/21/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "ViewController.h"
#import "SWRevealViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize sidebarButton;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    SWRevealViewController *revealViewController = self.revealViewController;
    
    // Set the side bar button action. When it's tapped, it will show up the sidebar.
    if (revealViewController) {
        [sidebarButton setTarget:self.revealViewController];
        [sidebarButton setAction:@selector(revealToggle:)];
        
        // Set the gesture
        [self.view addGestureRecognizer:self.revealViewController.panGestureRecognizer];
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
